package com.vkr.studentsapp.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.GroupMessage;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String groupName;
    private String imageURL;
    private String email;
    private String password;
}
