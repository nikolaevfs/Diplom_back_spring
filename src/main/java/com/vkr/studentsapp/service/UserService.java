package com.vkr.studentsapp.service;

import com.vkr.studentsapp.dto.RefreshTokenRequest;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.GroupMessage;
import com.vkr.studentsapp.model.Role;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.repository.GroupRepository;
import com.vkr.studentsapp.repository.RoleRepository;
import com.vkr.studentsapp.repository.UserRepository;
import com.vkr.studentsapp.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GroupService groupService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, GroupRepository groupRepository, BCryptPasswordEncoder passwordEncoder, GroupService groupService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupService = groupService;
    }


    public User addUser(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        user.setGroup(groupService.getGroupByName(user.getGroup().getName()));

        User addedUser = userRepository.save(user);

        System.out.println(user);
        return addedUser;
    }

    public User addAdmin(User user) {
        System.out.println("try to add admin");
        Role roleUser = roleRepository.findByName("ROLE_USER");
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        userRoles.add(roleAdmin);


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        Group group = new Group();

        group.setName(user.getGroup().getName());
        group.setDescription(user.getGroup().getDescription());

        groupRepository.save(group);
        user.setGroup(group);
        User addedUser = userRepository.save(user);

        System.out.println(user);
        return addedUser;
    }

    public List<User> findAll(){
        List<User> result = userRepository.findAll();
        System.out.println(result.size());
        return result;
    }

    public User findByUsername(String username){
        User result = userRepository.findUserByUsername(username);
        System.out.println("try to find user");
        System.out.println(result);
        return result;
    }


    public User findById(Long id){
        User result = userRepository.findUserById(id);
        System.out.println(result);
        return result;
    }

    public List<User> findAcceptedUsersInGroup(User user){
        return userRepository.findUsersByGroupAndAccepted(user.getGroup(), true);
    }

    public List<User> findNotAcceptedUsersInGroup(User user){
        List<User> users = userRepository.findUsersByGroup(user.getGroup());

        List<User> notAcceptedUsers = new ArrayList<User>();

        for(User usr : users){
            if(!usr.isAccepted()){
                notAcceptedUsers.add(usr);
            }
        }
        return notAcceptedUsers;
    }


    public void deleteUser(String username){
        userRepository.deleteUserByUsername(username);
    }


    public List<User> findUsersStartWith(String username){
        return userRepository.findByUsernameStartingWith(username);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }


}
