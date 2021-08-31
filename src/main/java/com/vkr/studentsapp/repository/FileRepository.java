package com.vkr.studentsapp.repository;

import com.vkr.studentsapp.model.File;
import com.vkr.studentsapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findFilesByGroup(Group group);

    @Transactional
    void deleteFileByName(String name);
}
