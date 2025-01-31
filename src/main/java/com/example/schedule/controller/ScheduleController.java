package com.example.schedule.controller;


import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")

public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    //일정 생성 API
    //@param : @Link ScheduleRequestDto 메모 생성 요청 객체
    //@return : @Link ResponseEntity<ScheduleReponseDto> JSON 응답
    @PostMapping
    public ResponseEntity<ScheduleResponseDto>createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return  new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    //일정 전체 조회 API
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedule() {
        return scheduleService.findAllSchedules();
    }

    //메모 단건 조회 API
    //@param name 식별자
    //@return : @link ResponseEntity<ScheduleResponseDto> JSON 응답
    //@exception ResponseStatuesException 식별자로 조회된 Memo가 없는 경우 404 Not Found
    @GetMapping("{/name}")
    public ResponseEntity<ScheduleResponseDto> findScheduleByName(@PathVariable String name) {
        return new ResponseEntity<>(scheduleService.findScheduleByName(name),HttpStatus.OK);
    }


    //메모 전체 수정 API
    //@param pw 식별자
    //@param @link ScheduleRequestDto 일정 수정요청 객체
    //@return @link ResponseEntity<ScheduleResponseDto> JSON 응답
    //@exception ResponseStatusExcpetion 요청 필수값이 없는 경우 400 Bad Request, 식별자로 조회된 Scehedule이 없는 경우 404 Not Found
    @PutMapping("{/name}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable String name,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(name,requestDto.getPw(),requestDto.getContents()),HttpStatus.OK);
    }

    //메모 삭제 API
    //@param pw 식별자
    //@return @link ResponseEntity<Void> 성공시 Data 없이 2000k 상태코드만 응답
    //@exception ResponseStatusException 식별자로 조회된 Schedule 이 없는 경우 404 Not Found
    @DeleteMapping("{/id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody(required = false)ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(id, dto.getPw());
        //성공한 경우
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
