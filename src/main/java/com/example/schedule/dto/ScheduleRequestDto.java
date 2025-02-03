package com.example.schedule.dto;

import lombok.Getter;

//schedule 요청 DTO
@Getter
public class ScheduleRequestDto {
    private String name;
    private String title;
    private String contents;
    private String pw;


}
