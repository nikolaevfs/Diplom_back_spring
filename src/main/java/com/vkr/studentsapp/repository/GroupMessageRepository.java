package com.vkr.studentsapp.repository;

import com.vkr.studentsapp.model.GroupMessage;
import com.vkr.studentsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {

    List<GroupMessage> findGroupMessagesByUser(User user);
}
