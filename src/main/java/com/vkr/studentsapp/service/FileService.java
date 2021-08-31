package com.vkr.studentsapp.service;

import com.vkr.studentsapp.model.File;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findAllGroupFiles(Group group){
        return fileRepository.findFilesByGroup(group);
    }

    public void addFile(File file){
        fileRepository.save(file);
    }

    public void delete(String fileName){
        fileRepository.deleteFileByName(fileName);
    }
}
