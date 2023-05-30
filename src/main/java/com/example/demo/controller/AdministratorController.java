package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.AdministratorService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    @PostMapping("/insertAdmin")
    public int insert(@RequestBody Administrator administrator) {
        return administratorService.insert(administrator);
    }

    @PostMapping("/saveCourse")
    public int saveCourse(@RequestBody Course course) {
        return administratorService.saveCourse(course);
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public int deleteCourse(@PathVariable String courseId) {
        return administratorService.deleteCourse(courseId);
    }

    @PostMapping("/saveStudent")
    public int saveStudent(@RequestBody Student student) {
        return administratorService.saveStudent(student);
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public int deleteStudent(@PathVariable String studentId) {
        return administratorService.deleteStudent(studentId);
    }

    @PostMapping("/saveTeacher")
    public int saveTeacher(@RequestBody Teacher teacher) {
        return administratorService.saveTeacher(teacher);
    }

    @PostMapping("/deleteTeacher/{teacherId}")
    public int deleteTeacher(@PathVariable String teacherId) {
        return administratorService.deleteTeacher(teacherId);
    }

    //新增内容
    @GetMapping("/checkMessage")
    public List<Message> checkMessage() {
        return this.administratorService.checkMessage();
    }

    @PostMapping("/acceptProposal")
    public int acceptProposal(@RequestBody Message oldMessage, @RequestBody Message newMessage) {
        return this.administratorService.accept(oldMessage, newMessage);
    }

    @PostMapping("/denyProposal")
    public int denyProposal(@RequestBody Message oldMessage, @RequestBody Message newMessage) {
        return this.administratorService.deny(oldMessage, newMessage);
    }
}
