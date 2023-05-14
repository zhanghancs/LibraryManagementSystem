package com.example.demo.controller;

import com.example.demo.entity.Course;
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
    @GetMapping("/check/{courseId}")
    public Course checkByCourseId(@PathVariable String courseId) {
        return courseService.checkById(courseId);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Course course) {
        return courseService.save(course);
    }

    @DeleteMapping("/delete/{courseId}")
    public boolean delete(@PathVariable String courseId) {
        return courseService.removeById(courseId);
    }
}
