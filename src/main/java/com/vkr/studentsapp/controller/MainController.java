package com.vkr.studentsapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping({"/", "/home", "/group", "/dialogs", "/documents",
            "/adminusers", "/admingroups"})
    public String home() {
        return "forward:/index.html";
    }
}
