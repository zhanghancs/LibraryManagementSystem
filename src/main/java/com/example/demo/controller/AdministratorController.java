package com.example.demo.controller;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Enrollment;
import com.example.demo.service.AdministratorService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    @PostMapping("/insert")
    public boolean insert(@RequestBody Administrator administrator) {
        return administratorService.insert(administrator);
    }
}
