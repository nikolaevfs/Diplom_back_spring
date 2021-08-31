package com.vkr.studentsapp.service;

import com.vkr.studentsapp.model.Message;
import com.vkr.studentsapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getDialogMessages(long firstStudentId, long secondStudentId){
        return messageRepository.findDialogMessages(firstStudentId, secondStudentId);
    }
}
