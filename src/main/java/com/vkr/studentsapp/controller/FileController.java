package com.vkr.studentsapp.controller;

import com.vkr.studentsapp.dto.GroupMessageDto;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.service.FileService;
import com.vkr.studentsapp.service.GroupService;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Controller
@RequestMapping("/file")
public class FileController {

    private UserService userService;
    private FileService fileService;
    private GroupService groupService;

    @Autowired
    public FileController(UserService userService, FileService fileService, GroupService groupService) {
        this.userService = userService;
        this.fileService = fileService;
        this.groupService = groupService;
    }


    public static final String DIRECTORY = System.getProperty("user.dir") + "/Uploads/";

    @PostMapping("/upload/{username}")
    public ResponseEntity<List<String>> uploadFiles(@PathVariable("username") String username, @RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {

        User user = userService.findByUsername(username);
        String groupName = user.getGroup().getName();
        Group group = groupService.getGroupByName(groupName);

        String newDirectory = DIRECTORY + groupName;

        if(Files.exists(get(DIRECTORY, groupName).toAbsolutePath().normalize())){
            List<String> filenames = new ArrayList<>();
            for(MultipartFile file : multipartFiles) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());

                com.vkr.studentsapp.model.File fileModel = new com.vkr.studentsapp.model.File(null, filename, group);
                fileService.addFile(fileModel);

                Path fileStorage = get(newDirectory, filename).toAbsolutePath().normalize();
                System.out.println(fileStorage);
                copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
                filenames.add(filename);
            }
            return ResponseEntity.ok().body(filenames);
        }
        File dir = new File(String.valueOf(get(DIRECTORY, groupName).toAbsolutePath().normalize()));
        dir.mkdir();


        List<String> filenames = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(newDirectory, filename).toAbsolutePath().normalize();
            System.out.println(fileStorage);
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }


    @GetMapping("download/{username}/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename, @PathVariable("username") String username) throws IOException {

        User user = userService.findByUsername(username);
        String groupName = user.getGroup().getName();

        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(groupName).resolve(filename);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        System.out.println(filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }


    @GetMapping("allfiles/{username}")
    public ResponseEntity<String[]> allGroupFiles(@PathVariable("username") String username) throws IOException {

        User user = userService.findByUsername(username);
        Group group = user.getGroup();

        List<com.vkr.studentsapp.model.File> files = new ArrayList<>(fileService.findAllGroupFiles(group));

        List<String> fileNames = new ArrayList<>();
        for(com.vkr.studentsapp.model.File file : files){
            fileNames.add(file.getName());
        }

        String[] fileNamesArray = new String[fileNames.size()];
        fileNames.toArray(fileNamesArray);

        return new ResponseEntity<>(fileNamesArray, HttpStatus.OK);
    }



    @PostMapping("delete/{username}")
    public ResponseEntity<String[]> deleteFile(@PathVariable("username") String username, @RequestBody String fileName) throws IOException {
        System.out.println("we a re here");
        System.out.println(fileName);

        User user = userService.findByUsername(username);
        Group group = user.getGroup();

        fileService.delete(fileName);

        File deleteFile = new File(String.valueOf(get(DIRECTORY, group.getName(),fileName).toAbsolutePath().normalize()));
        if(deleteFile.delete()){
            System.out.println("файл удален");
        }else System.out.println("не обнаружено");

        List<com.vkr.studentsapp.model.File> files = new ArrayList<>(fileService.findAllGroupFiles(group));

        List<String> fileNames = new ArrayList<>();
        for(com.vkr.studentsapp.model.File file : files){
            fileNames.add(file.getName());
        }

        String[] fileNamesArray = new String[fileNames.size()];
        fileNames.toArray(fileNamesArray);

        return new ResponseEntity<>(fileNamesArray, HttpStatus.OK);
    }
}
