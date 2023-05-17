package com.example.demo.service;

import cn.hutool.core.util.StrUtil;
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

    public int insert(Enrollment enrollment) {
        if (checkById(enrollment)) return 0;
        if (enrollmentMapper.insert(enrollment) == 0) return 0;
        return courseMapper.increaseSelectedCount(enrollment.getCourseId());
    }
    public int deleteOne(Enrollment enrollment){
        if (!checkById(enrollment)) return 0;
        if (enrollmentMapper.deleteOne(enrollment) == 0) return 0;
        return courseMapper.reduceSelectedCount(enrollment.getCourseId());
    }
    private boolean checkById(Enrollment enrollment) {
        String courseId = enrollment.getCourseId();
        String studentId = enrollment.getStudentId();
        if (StrUtil.isBlank(courseId) || StrUtil.isBlank(studentId)) {
            return false;
        }
        return enrollmentMapper.check(enrollment) != null;
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

    public List<Course> checkCanChooseCourse(String studentId, String type) {
        List<Course> courseList = courseMapper.checkAll();
        List<Course> courses = new ArrayList<>();
        // 入学年份
        int year = Integer.parseInt(studentId.substring(0,2));
        // 年级
        int grade = Integer.parseInt(type.substring(0,2)) - year + 1;
        int semester = type.charAt(2) - '0';

        int major = Integer.parseInt(type.substring(3,5));
        for (Course item : courseList) {
            String courseId = item.getCourseId();
            int courseGrade = courseId.charAt(7) - '0';
            int courseMajor = Integer.parseInt(courseId.substring(5,7));
            int courseSemester = courseId.charAt(8) - '0';
            if (courseGrade == grade && courseSemester == semester && courseMajor == major) {
                courses.add(item);
            }
        }
        return courses;
    }


}
