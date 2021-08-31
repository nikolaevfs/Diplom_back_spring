package com.vkr.studentsapp.controller;

import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.model.UserOnly;
import com.vkr.studentsapp.service.GroupService;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/superadmin/")
public class SuperAdminController {

    private UserService userService;
    private GroupService groupService;

    @Autowired
    public SuperAdminController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("allusers")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> userList = userService.findAll();

        List<User> withoutSuper = new ArrayList<User>();
        for(User user : userList){
            if(user.getRoles().size()!=3){
                withoutSuper.add(user);
            }
        }

        return new ResponseEntity<>(withoutSuper, HttpStatus.OK);
    }

    @GetMapping("allgroups")
    public ResponseEntity<List<Group>> getAllGroups() {

        List<Group> groupList = groupService.getAllGroups();
        System.out.println(groupList);
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @GetMapping("students/{username}")
    public ResponseEntity<List<User>> getSomeStudents(@PathVariable(name="username") String username) {

        List<User> userList = userService.findUsersStartWith(username);


        List<User> withoutSuper = new ArrayList<User>();
        for(User user : userList){
            if(user.getRoles().size()!=3){
                withoutSuper.add(user);
            }
        }
        return new ResponseEntity<>(withoutSuper, HttpStatus.OK);
    }


    @GetMapping("groups/{name}")
    public ResponseEntity<List<Group>> getSomeGroups(@PathVariable(name="name") String name) {
        List<Group> groupList = groupService.getSomeGroups(name);


        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }



    @DeleteMapping("deletegroup/{name}")
    public void deleteUser(@PathVariable(name="name") String name) {
        System.out.println("try to delete group");
        groupService.deleteGroup(name);

    }

}
