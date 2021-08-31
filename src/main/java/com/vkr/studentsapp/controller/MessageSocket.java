package com.vkr.studentsapp.controller;


import com.vkr.studentsapp.dto.GroupMessageDto;
import com.vkr.studentsapp.model.GroupMessage;
import com.vkr.studentsapp.service.GroupMessageService;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class MessageSocket {

    private UserService userService;
    private GroupMessageService groupMessageService;


    @Autowired
    public MessageSocket(UserService userService, GroupMessageService groupMessageService) {
        this.userService = userService;
        this.groupMessageService = groupMessageService;
    }


    @MessageMapping("/send/newmessage")
   // @SendTo({"/topic/newmessage"})
    public void changes(@Payload String group) {

        System.out.println("messageMapping");

        System.out.println(group);

    }

}
