package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        //요청받은 데이터로 Schedule 객체 생성 ID없음
        Schedule schedule = new Schedule(requestDto.getName(),requestDto.getPw(),requestDto.getContents());

        //Inmemory DB에 Schedule 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        //전체 조회
        List<ScheduleResponseDto> allSchedules = scheduleRepository.findAllSchedules();
        return allSchedules;
    }

    @Override
    public ScheduleResponseDto findScheduleByName(String name) {
        //식별자의 Schedule이 없다면?
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(name);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(String name, String pw, String contents) {
        //필수값 검증
        if (pw == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The pw and contents are required values.");
        }

        int updatedRow = scheduleRepository.updateSchedule(name, pw, contents);

        // NPE 방지
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist name = " + name);
        }

        //  schedule 수정
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(name);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String pw) {
        //schedule 삭제

        int deleteRow = scheduleRepository.deleteschedule(id);

        //NPE 방지 삭제된 row가 0개라면

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }



}
