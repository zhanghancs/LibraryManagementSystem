package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/check")
    public List<Student> checkAll() {
        return studentService.checkAll();
    }

    @GetMapping("/checkPage/{page}/{size}")
    public List<Student> findByPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return studentService.checkByPage(page, size);
    }
    @GetMapping("/check/{studentId}")
    public Student checkById(@PathVariable String studentId) {
        return studentService.checkById(studentId);
    }



}
