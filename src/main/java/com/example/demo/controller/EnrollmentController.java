package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Student;
import com.example.demo.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {


    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping("/insert")
    public Integer insert(@RequestBody Enrollment enrollment) {
        return enrollmentService.insert(enrollment);
    }

    @GetMapping("/checkCourse/{studentId}")
    public List<Course> checkCourse(@PathVariable String studentId) {
        return enrollmentService.checkCourse(studentId);
    }

    @GetMapping("/checkCanChooseCourse/{studentId}")
    public List<Course> checkCanChooseCourse(@PathVariable String studentId) {
        return enrollmentService.checkCanChooseCourse(studentId);
    }

    @GetMapping("/checkStudent/{courseId}")
    public List<Student> checkStudent(@PathVariable String courseId) {
       return enrollmentService.checkStudent(courseId);
    }

    @DeleteMapping("/delete")
    public int deleteOne(@RequestBody Enrollment enrollment){
        return enrollmentService.deleteOne(enrollment);
    }

    @GetMapping("/checkTime")
    public String checkTime() {
        return enrollmentService.checkTime();
    }

    @PostMapping("/setTime/{tim}")
    public int setTime(@PathVariable String tim) {
        return enrollmentService.setTime(tim);
    }
}
