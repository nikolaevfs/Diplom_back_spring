package com.vkr.studentsapp.repository;

import com.vkr.studentsapp.model.Event;
import com.vkr.studentsapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByGroup(Group group);
}
