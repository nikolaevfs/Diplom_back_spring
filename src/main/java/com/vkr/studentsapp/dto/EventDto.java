package com.vkr.studentsapp.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class EventDto {
    private String title;
    private Date start;
    private Date end;

    public EventDto(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }
}
