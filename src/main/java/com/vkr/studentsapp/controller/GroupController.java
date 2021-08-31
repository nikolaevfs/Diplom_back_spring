package com.vkr.studentsapp.controller;

import com.vkr.studentsapp.dto.AuthenticationRequestDto;
import com.vkr.studentsapp.dto.EventDto;
import com.vkr.studentsapp.model.Event;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.model.UserOnly;
import com.vkr.studentsapp.service.EventService;
import com.vkr.studentsapp.service.FileService;
import com.vkr.studentsapp.service.GroupService;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/group")
public class GroupController {


    private GroupService groupService;
    private UserService userService;
    private EventService eventService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, EventService eventService) {
        this.groupService = groupService;
        this.userService = userService;
        this.eventService = eventService;
    }


    @GetMapping("/students/{username}")
    public ResponseEntity<List<User>> getUserByUsername(@PathVariable(name="username") String username) {
        User user = userService.findByUsername(username);

        List<User> userList = userService.findAcceptedUsersInGroup(user);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    @GetMapping("/events/{username}")
    public ResponseEntity<List<EventDto>> getGroupEvents(@PathVariable(name="username") String username) {
        User user = userService.findByUsername(username);
        Group group = user.getGroup();

        List<Event> events = eventService.findGroupEvents(group);

        List<EventDto> eventDtos = new ArrayList<EventDto>();
        for(Event event : events){
            EventDto ev= new EventDto(event.getTitle(),event.getStart(), event.getEnd());
            eventDtos.add(ev);
        }

        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @PostMapping("/events/add/{username}")
    public ResponseEntity<List<EventDto>> addGroupEvents(@PathVariable(name="username") String username,
                                                         @RequestBody EventDto eventDto) {

        System.out.println("adding event");
        User user = userService.findByUsername(username);
        Group group = user.getGroup();

        Event event = new Event(eventDto.getTitle(),eventDto.getStart(),eventDto
        .getEnd(),group);

        this.eventService.addEvent(event);

        List<Event> events = eventService.findGroupEvents(group);

        List<EventDto> eventDtos = new ArrayList<EventDto>();
        for(Event eventt : events){
            EventDto ev= new EventDto(eventt.getTitle(),eventt.getStart(), eventt.getEnd());
            eventDtos.add(ev);
        }

        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }


}
