package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {


    //할일,작성자명,비밀번호,작성/수정일
    private Long id;
    private String name;
    private String contents;
    private String pw;

    //작성/수정일
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public Schedule(String name,String pw,String contents) {
        this.name = name;
        this.pw = pw;
        this.contents = contents;
    }

    public void update(String name, String pw, String contents) {
        this.name = name;
        this.pw = pw;
        this.contents = contents;
    }

}
