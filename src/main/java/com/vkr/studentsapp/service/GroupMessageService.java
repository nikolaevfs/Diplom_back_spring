package com.vkr.studentsapp.service;

import com.vkr.studentsapp.model.GroupMessage;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.repository.GroupMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMessageService {
    private final GroupMessageRepository groupMessageRepository;

    @Autowired
    public GroupMessageService(GroupMessageRepository groupMessageRepository) {
        this.groupMessageRepository = groupMessageRepository;
    }

    public GroupMessage addGroupMessage(GroupMessage groupMessage){
        groupMessageRepository.save(groupMessage);
        return groupMessage;
    }

    public List<GroupMessage> findGroupMessages(User user){
        return groupMessageRepository.findGroupMessagesByUser(user);
    }

}
