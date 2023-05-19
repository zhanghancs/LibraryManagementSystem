package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/check")
    public List<Course> checkAll() {
        return courseService.checkAll();
    }

    @GetMapping("/checkPage/{page}/{size}")
    public List<Course> findByPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return courseService.checkByPage(page, size);
    }
    @GetMapping("/checkCourse/{courseId}")
    public Course checkById(@PathVariable String courseId) {
        return courseService.checkById(courseId);
    }

    @GetMapping("/checkCourseCount/{courseId}")
    public int checkCourseCount(@PathVariable String courseId) {
        return courseService.checkCourseCount(courseId);
    }


//    @PostMapping("/save")
//    public int save(@RequestBody Course course) {
//        return courseService.save(course);
//    }


}
