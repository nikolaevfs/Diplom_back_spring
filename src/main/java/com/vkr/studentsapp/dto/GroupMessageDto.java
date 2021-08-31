package com.vkr.studentsapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GroupMessageDto implements Comparable<GroupMessageDto>{
    private String firstName;
    private String lastName;
    private String username;
    private String text;
    private Date timeSent;

    @Override
    public int compareTo(GroupMessageDto o) {
        return getTimeSent().compareTo(o.getTimeSent());
    }
}
