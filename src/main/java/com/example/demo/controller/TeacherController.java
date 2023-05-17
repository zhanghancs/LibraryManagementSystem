package com.example.demo.controller;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/check")
    public List<Teacher> checkAll() {
        return teacherService.checkAll();
    }

    @GetMapping("/checkPage/{page}/{size}")
    public List<Teacher> findByPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return teacherService.checkByPage(page, size);
    }
    @GetMapping("/check/{teacherId}")
    public Teacher checkById(@PathVariable String teacherId) {
        return teacherService.checkById(teacherId);
    }

//    @PostMapping("/save")
//    public int save(@RequestBody Teacher teacher) {
//        return teacherService.save(teacher);
//    }
//
//    @DeleteMapping("/delete/{teacherId}")
//    public int delete(@PathVariable String teacherId) {
//        return teacherService.removeById(teacherId);
//    }
}
