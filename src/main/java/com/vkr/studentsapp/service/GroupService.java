package com.vkr.studentsapp.service;

import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups(){
        return groupRepository.findAllByOrderByNameAsc();
    }

    public void deleteGroup(String name){
        groupRepository.deleteGroupByName(name);
    }

    public List<Group> getSomeGroups(String name){
        return groupRepository.findGroupByNameStartingWith(name);
    }

    public Group getGroupByName(String name){
        return groupRepository.findGroupByName(name);
    }
}
