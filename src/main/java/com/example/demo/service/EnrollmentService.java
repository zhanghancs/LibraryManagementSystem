package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Student;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.EnrollmentMapper;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    EnrollmentMapper enrollmentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;

    public boolean insert(Enrollment enrollment) {
        return enrollmentMapper.insert(enrollment);
    }

    public List<Course> checkCourse(String studentId) {
        List<Enrollment> enrollmentList = enrollmentMapper.checkCourse(studentId);
        List<Course> courseList = new ArrayList<Course>();
        for (Enrollment item : enrollmentList) {
            courseList.add(courseMapper.checkByCourseId(item.getCourseId()));
        }
        return courseList;
    }

    public List<Student> checkStudent(String courseId) {
        List<Enrollment> enrollmentList = enrollmentMapper.checkStudent(courseId);
        List<Student> studentList = new ArrayList<Student>();
        for (Enrollment item : enrollmentList) {
            studentList.add(studentMapper.checkById(item.getStudentId()));
        }
        return studentList;
    }

    public boolean deleteOne(Enrollment enrollment){
        return enrollmentMapper.deleteOne(enrollment);
    }
}
