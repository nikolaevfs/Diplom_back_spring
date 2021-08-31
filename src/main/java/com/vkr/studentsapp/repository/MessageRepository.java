package com.vkr.studentsapp.repository;

import com.vkr.studentsapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "" +
            "SELECT * " +
            "FROM messages m " +
            "WHERE m.id_from = ?1 AND m.id_to = ?2 " +
            "UNION " +
            "SELECT * " +
            "FROM messages m " +
            "WHERE m.id_from = ?2 AND m.id_to = ?1" +
            "ORDER BY 5", nativeQuery = true
    )
    List<Message> findDialogMessages(long firstStudentId, long secondStudentId);
}
