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

    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.repository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        //요청받은 데이터로 Schedule 객체 생성 ID없음
        Schedule schedule = new Schedule(requestDto.getName(),requestDto.getPw(),requestDto.getContents());

        //Inmemory DB에 Schedule 저장
        return repository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateAt, String name) {
        //전체 조회
        return repository.findAllSchedules(updateAt,name);
    }

    @Override
    public ScheduleResponseDto findScheduleByName(Long id) {
        //식별자의 Schedule이 없다면?
        Schedule schedule = repository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id,String title, String name,String pw, String contents) {
        //필수값 검증
        if (pw == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The pw and contents are required values.");
        }
        validPassword(id,pw);

        int updatedRow = repository.updateSchedule(id,title,name,pw,contents);

        // NPE 방지
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist name = " + id);
        }

        //  schedule 수정
        Schedule schedule = repository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String pw) {
        //schedule 삭제

        int deleteRow = repository.deleteschedule(id,pw);

        //NPE 방지 삭제된 row가 0개라면

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    @Override
    public void validPassword(Long id, String pw) {
        if (!repository.validPassword(id, pw)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

    }


}
