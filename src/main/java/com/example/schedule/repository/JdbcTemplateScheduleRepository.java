package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbc;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbc);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("title",schedule.getTitle());
        parameters.put("name",schedule.getName());
        parameters.put("pw",schedule.getPw());
        parameters.put("contents",schedule.getContents());
        parameters.put("updated_At",LocalDateTime.now());
        parameters.put("created_At",LocalDateTime.now());
        parameters.put("is_deleted", 0); // 기본값: 삭제되지 않음
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        System.out.println("schedule = " + schedule.getName());
        return new ScheduleResponseDto(key.longValue(), schedule.getTitle(), schedule.getName(), schedule.getContents());
    }

    //일정 전체 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateAt, String name) {
        String sql;

        // 수정일 파라미터만 입력된 경우
        if (updateAt != null && name == null){
            sql = "SELECT * FROM schedule " +
                    "WHERE DATE(updated_at)= ? AND is_deleted = 0 \n" +
                    "ORDER BY updated_at DESC";
            return jdbc.query(sql,scheduleRowMapper(),updateAt);
        }

        // 사용자 id 파라미터만 입력된 경우
        if (updateAt == null && name != null){
            sql = "SELECT * FROM schedule " +
                    "WHERE name = ? AND is_deleted = 0 \n" +
                    "ORDER BY updated_at DESC";
            return jdbc.query(sql,scheduleRowMapper(),name);
        }

        // 수정일, 사용자 id 파라미터 둘다 입력된 경우
        if (updateAt != null && name != null){
            sql = "SELECT * FROM schedule \n" +
                    "WHERE DATE(updated_at)= ? AND name = ?  AND is_deleted = 0 \n" +
                    "ORDER BY updated_at DESC";
            return jdbc.query(sql,scheduleRowMapper(),updateAt,name);
        }

        // 파라미터가 둘다 입력되지 않은 경우
        sql = "SELECT * FROM schedule " +
                "WHERE is_deleted = 0 \n"+
                "ORDER BY updated_at DESC";

        return jdbc.query(sql,scheduleRowMapper());
    }

    //단일 일정 조회
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule>result = jdbc.query("select * from schedule where id = ? AND is_deleted = 0 ",scheduleRowMapperV2(),id);
        return result.stream().findAny();
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule>result = jdbc.query("select * from schedule where id = ? AND is_deleted = 0 ",scheduleRowMapperV2(),id);
        return result.stream().findAny().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));

    }

    @Override
    public int updateSchedule(Long id,String title,String name,String pw, String contents) {
        int result = jdbc.update("update schedule set title = ? , contents = ? , name = ? where id = ? and pw = ? and is_deleted = 0 ", title, contents, name, id, pw);
        return result;
    }

    @Override
    public int deleteschedule(Long id,String pw) {

        return jdbc.update("UPDATE schedule SET is_deleted = 1 WHERE id = ? AND pw = ?",id,pw);
    }

    @Override
    public boolean validPassword(Long id, String pw) {
        String sql = "select count(*) from schedule where id = ? and pw = ? and is_deleted = 0";
        Integer count = jdbc.queryForObject(sql,Integer.class,id,pw);
        return (count > 0) ? true : false;
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("contents")
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
    }
}
