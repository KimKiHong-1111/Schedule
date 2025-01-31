package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Optional<Schedule> findScheduleByName(String name);

    Schedule findScheduleByIdOrElseThrow(String name);

    int updateSchedule(String name, String pw, String contents);

    int deleteschedule(Long id);

}
