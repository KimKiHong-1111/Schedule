package com.example.schedule.entity;

import com.example.schedule.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    //할일,작성자명,비밀번호
    private Long id;
    private String name;
    private String title;
    private String contents;
    private String pw;

    //작성/수정일
    private LocalDateTime createdDate = LocalDateTime.now(); //작성일
    private LocalDateTime updatedDate = createdDate; //수정일

    public Schedule(Long id, String name, String title, String contents) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.contents = contents;
    }

    //일정 생성자 네임,타이틀
    public Schedule(String name,String title,String pw,String contents) {
        this.name = name;
        this.title = title;
        this.pw = pw;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = this.createdDate;
    }

    //업데이트
    public void update(ScheduleRequestDto requestDto) {
        this.name = name;
        this.pw = pw;
        this.contents = contents;
        this.updatedDate = LocalDateTime.now();
    }

}
