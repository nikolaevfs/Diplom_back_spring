package com.vkr.studentsapp.controller;


import com.vkr.studentsapp.dto.AuthenticationRequestDto;
import com.vkr.studentsapp.dto.UserDto;
import com.vkr.studentsapp.model.*;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{username}")
    public ResponseEntity<UserOnly> getUserByUsername(@PathVariable(name="username") String username) {

        User user = userService.findByUsername(username);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserOnly result = new UserOnly(user.getId(), user.getFirstName(), user.getLastName(),user.getUsername(), user.getGroup(),user.getImageURL(),user.getEmail(),user.getPassword(),user.getRoles());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("roles/{username}")
    public ResponseEntity<Integer> getUsers(@PathVariable(name="username") String username) {

        User user = userService.findByUsername(username);
        Integer result = user.getRoles().size();
        if(result ==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("delete/{username}")
    public void deleteUser(@PathVariable(name="username") String username) {
        System.out.println("try to delete");
        userService.deleteUser(username);

    }

    @PatchMapping("update/{username}")
    public ResponseEntity<UserOnly> updateUser(@PathVariable(name="username") String username, @RequestBody UserDto requestDto) {
        System.out.println("try to update");
        User user = userService.findByUsername(username);
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        userService.updateUser(user);

        UserOnly result = new UserOnly(user.getId(), user.getFirstName(), user.getLastName(),user.getUsername(),
                user.getGroup(),user.getImageURL(),user.getEmail(),user.getPassword(),user.getRoles());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
