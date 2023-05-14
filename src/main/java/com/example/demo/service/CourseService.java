package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;

    public List<Course> checkAll() {
        return  courseMapper.checkAll();
    }

    public Course checkById(String id) {

        return courseMapper.checkByCourseId(id);
    }

    public boolean save(Course course) {
        if (courseMapper.checkByCourseId(course.getCourseId()) == null) {
            return courseMapper.insert(course);
        } else {
            return courseMapper.update(course);
        }
    }

    public boolean removeById(String id) {
        if (courseMapper.checkByCourseId(id) == null) return false;
        return courseMapper.removeById(id);
    }

    public List<Course> checkByPage(Integer page, Integer size) {
        List<Course> courseList = courseMapper.checkAll();
        ArrayList<Course> list = new ArrayList<Course>();
        int sz = courseList.size();

        int start = size * page;
        int end = size * (page + 1) < sz ? size*(page + 1) : sz;

        for (int i = start; i < end; i++) {
            list.add(courseList.get(i));
        }

        return list;
    }
}
