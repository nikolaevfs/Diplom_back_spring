package com.vkr.studentsapp.repository;

import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserById(Long id);

    List<User> findUsersByGroupAndAccepted(Group group, boolean accepted);

    List<User> findUsersByGroup(Group group);

    List<User> findByUsernameStartingWith(String username);

    @Transactional
    void deleteUserByUsername(String username);


}
