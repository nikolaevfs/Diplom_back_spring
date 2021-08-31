package com.vkr.studentsapp.controller;

import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@RestController
@RequestMapping(value = "api/admin/")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("accepting/{username}")
    public ResponseEntity<List<User>> getUsersForAccepting(@PathVariable(name="username") String username) {
        User user = userService.findByUsername(username);

        List<User> userList = userService.findNotAcceptedUsersInGroup(user);
        System.out.println("getting for accept");
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("accept/{username}")
    public ResponseEntity<List<User>> acceptUser(@PathVariable(name="username") String username) {
        User user = userService.findByUsername(username);

        user.setAccepted(true);
        userService.updateUser(user);

        List<User> userList = userService.findAcceptedUsersInGroup(user);

        return new ResponseEntity<>(userList, HttpStatus.OK);

    }
}
