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

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        return null;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return List.of();
    }

    @Override
    public Optional<Schedule> findScheduleByName(String name) {
        return Optional.empty();
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(String name) {
        return null;
    }

    @Override
    public int updateSchedule(String name, String pw, String contents) {
        return 0;
    }

    @Override
    public int deleteschedule(Long id) {
        return 0;
    }

}
