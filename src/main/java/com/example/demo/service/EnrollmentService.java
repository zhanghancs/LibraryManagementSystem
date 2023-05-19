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
        String courseId = enrollment.getCourseId();
        String studentId = enrollment.getStudentId();
        Course course = courseMapper.checkById(courseId);
        // 判断课容量是否满了
        if (course.getSelectedCount() >= course.getCapacity()) return 0;
        // 增加课程已选人数
        if (courseMapper.increaseSelectedCount(courseId) == 0) return 0;
        // 增加学生的学分
        if(studentMapper.increaseCredits(studentId, course.getCredits()) == 0) return 0;
        // 增加选课记录
        return enrollmentMapper.insert(enrollment);
    }
    public int deleteOne(Enrollment enrollment){
        // 查找该选课记录
        if (!checkById(enrollment)) return 0;
        String courseId = enrollment.getCourseId();
        String studentId = enrollment.getStudentId();
        // 减少课程选课人数
        if (courseMapper.reduceSelectedCount(enrollment.getCourseId()) == 0) return 0;
        // 减少学生的学分
        Course course = courseMapper.checkById(courseId);
        if (studentMapper.reduceCredits(studentId, course.getCredits()) == 0) return 0;
        // 删除选课记录
        return enrollmentMapper.deleteOne(enrollment);

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
            courseList.add(courseMapper.checkById(item.getCourseId()));
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

        int major = Integer.parseInt(studentId.substring(3,5));
        for (Course item : courseList) {
            String courseId = item.getCourseId();
            int courseMajor = Integer.parseInt(courseId.substring(5,7));
            int courseGrade = courseId.charAt(7) - '0';
            int courseSemester = courseId.charAt(8) - '0';
            if (courseGrade == grade && courseSemester == semester && courseMajor == major) {
                courses.add(item);
            }
        }
        return courses;
    }

    public int deleteCourse(String courseId) {
        List<Enrollment> enrollmentList = enrollmentMapper.checkStudent(courseId);
        int ans = 0;
        for (Enrollment item: enrollmentList) {
            ans += 1;
            Course course = courseMapper.checkById(courseId);
            // 减少学生的学分
            if (studentMapper.reduceCredits(item.getStudentId(), course.getCredits()) == 0) return 0;
            if (enrollmentMapper.deleteOne(item) == 0) return 0;
        }
        return ans;
    }

    public int deleteStudent(String studentId) {
        List<Enrollment> enrollmentList = enrollmentMapper.checkStudent(studentId);
        int ans = 0;
        for (Enrollment item: enrollmentList) {
            ans += 1;
            // 减少课程选课人数
            if (courseMapper.reduceSelectedCount(item.getCourseId()) == 0) return 0;
            if (enrollmentMapper.deleteOne(item) == 0) return 0;
        }
        return ans;
    }
}
