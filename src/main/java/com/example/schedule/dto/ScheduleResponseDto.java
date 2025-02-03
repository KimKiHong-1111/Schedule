package com.example.schedule.dto;


import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    //id,contents,작성자,제목
    private Long id;
    //사용자명
    private String name;
    //일정내용
    private String contents;
    private String title;
    //작성,수정일
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String pw;

    public ScheduleResponseDto(Schedule schedule) {
        this.name = schedule.getName();
        this.pw = schedule.getPw();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedDate();
        this.updatedAt = schedule.getUpdatedDate();
    }
}
