package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules(String updateAt,String name);

    ScheduleResponseDto findScheduleByName(Long id);

    ScheduleResponseDto updateSchedule(Long id, String title ,String name,String pw, String contents);

    void deleteSchedule(Long id ,String pw);

    void validPassword(Long id,String pw);

}
