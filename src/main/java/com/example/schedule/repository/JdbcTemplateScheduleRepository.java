package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbc;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        return null;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateAt, String name) {
        return List.of();
    }

    @Override
    public Optional<Schedule> findScheduleByName(String name) {
        return Optional.empty();
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        return null;
    }

    @Override
    public int updateSchedule(Long id,String title,String name,String pw, String contents) {
        int result = jdbc.update("update schedule set tilte = ? , contents = ? , name = ? where id = ? and pw = ? and is_deleted = 0", title, contents, name, id, pw);
        return result;
    }

    @Override
    public int deleteschedule(Long id,String pw) {
        return 0;
    }

    @Override
    public boolean validPassword(Long id, String pw) {
        String sql = "select count(*) from schedule where id = ? and pw = ? and is_deleted = 0";
        Integer count = jdbc.queryForObject(sql,Integer.class,id,pw);
        return (count > 0) ? true : false;
    }

}
