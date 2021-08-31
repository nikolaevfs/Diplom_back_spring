package com.vkr.studentsapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.vkr.studentsapp.dto.AuthenticationRequestDto;
import com.vkr.studentsapp.dto.RefreshTokenRequest;
import com.vkr.studentsapp.dto.RegistrationDto;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.security.jwt.JwtTokenProvider;
import com.vkr.studentsapp.service.GroupService;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/")
public class LoginController {
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;
    private GroupService groupService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, GroupService groupService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("login")
    public ResponseEntity groups(){
        System.out.println("login and getting groups");
        List<Group> groups = new ArrayList<Group>(groupService.getAllGroups());
        List<String> groupNames = new ArrayList<String>();
        for(Group group : groups){
            groupNames.add(group.getName());
        }
        return ResponseEntity.ok(groupNames);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try{

            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if(user == null){
                throw new UsernameNotFoundException("User with username not found");
            }

            if (!user.isAccepted()) {
                throw new UsernameNotFoundException("User not accepted");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Long expiresAt = jwtTokenProvider.getValidityInMilliseconds();

            Map<Object ,Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            response.put("firstName", user.getFirstName());
            response.put("expiresAt", expiresAt);

            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }



    @PostMapping("registration/user")
    public ResponseEntity registration(@RequestBody RegistrationDto registrationDto) {
        try{
            Group group=new Group(null, registrationDto.getGroupName(),"Add description");
            User user = new User(registrationDto.getFirstName(),registrationDto.getLastName(),registrationDto.getUsername(),
                    group,registrationDto.getImageURL(),registrationDto.getEmail(),registrationDto.getPassword());
            User addingUser =
            userService.addUser(user);

            return ResponseEntity.ok("added");
        }catch(Error e){
            throw new BadCredentialsException("Error");
        }
    }

    @PostMapping("registration/admin")
    public ResponseEntity registrationAdmin(@RequestBody RegistrationDto registrationDto) {
        try{

            Group group=new Group(null, registrationDto.getGroupName(),"Add description");
            User user = new User(registrationDto.getFirstName(),registrationDto.getLastName(),registrationDto.getUsername(),
                    group,registrationDto.getImageURL(),registrationDto.getEmail(),registrationDto.getPassword());
            user.isAccepted();
            User addingUser =
                    userService.addAdmin(user);

            return ResponseEntity.ok("added");
        }catch(Error e){
            throw new BadCredentialsException("Error");
        }
    }
}
