package com.vkr.studentsapp.repository;

import com.vkr.studentsapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByOrderByNameAsc();

    Group findGroupByName(String name);

    List<Group> findGroupByNameStartingWith(String name);

    @Transactional
    void deleteGroupByName(String name);

}
