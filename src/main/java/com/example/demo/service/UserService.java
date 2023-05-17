package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
import com.example.demo.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    AdministratorService administratorService;

    public Integer login(String id, String password) {
        if (administratorService.isValidId(id)) {
            return administratorService.login(id, password);
        } else if(teacherService.isValidId(id)) {
            return teacherService.login(id, password);
        } else {
            return studentService.login(id, password);
        }
    }
}
