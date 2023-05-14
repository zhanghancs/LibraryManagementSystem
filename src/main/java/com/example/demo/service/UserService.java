package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    public Integer login(String id, String password) {
        if (id.startsWith("1")) {
            return 1;
        } else if(id.startsWith("2")) {
            return teacherService.login(id, password);
        } else if(id.startsWith("3")) {
            return studentService.login(id, password);
        } else {
            return 0;
        }
    }
}
