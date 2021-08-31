package com.vkr.studentsapp.controller;


import com.vkr.studentsapp.dto.AuthenticationRequestDto;
import com.vkr.studentsapp.dto.GroupMessageDto;
import com.vkr.studentsapp.model.GroupMessage;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.model.UserOnly;
import com.vkr.studentsapp.service.GroupMessageService;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/dialogs/")
public class DialogController {

    private UserService userService;
    private GroupMessageService groupMessageService;


    @Autowired
    public DialogController(UserService userService, GroupMessageService groupMessageService) {
        this.userService = userService;
        this.groupMessageService = groupMessageService;
    }



    @PostMapping("group/add")
    public void add(@RequestBody GroupMessageDto requestDto) {

        System.out.println("try to add message");

        System.out.println(requestDto);

        GroupMessage groupMessage = new GroupMessage(null, requestDto.getText(), requestDto.getTimeSent(), userService.findByUsername(requestDto.getUsername()));
        groupMessageService.addGroupMessage(groupMessage);
    }

    @GetMapping("group/{username}")
    public ResponseEntity<List<GroupMessageDto>> getGroupMessages(@PathVariable(name="username") String username) {
        User user = userService.findByUsername(username);

        List<User> usersInGroup = userService.findAcceptedUsersInGroup(user);

        List<GroupMessageDto> groupMessageDtoList = new ArrayList<>();

        for(User user1 : usersInGroup){

            for(GroupMessage groupMessage1 : groupMessageService.findGroupMessages(user1)){
                GroupMessageDto groupMessageDto = new GroupMessageDto();
                groupMessageDto.setFirstName(user1.getFirstName());
                groupMessageDto.setLastName(user1.getLastName());
                groupMessageDto.setText(groupMessage1.getText());
                groupMessageDto.setUsername(user1.getUsername());
                groupMessageDto.setTimeSent(groupMessage1.getTimeSent());

                groupMessageDtoList.add(groupMessageDto);
            }
        }

        Collections.sort(groupMessageDtoList);


        return new ResponseEntity<>(groupMessageDtoList, HttpStatus.OK);
    }


}
