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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EnrollmentService {
    @Autowired
    EnrollmentMapper enrollmentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;

    private String type = "231";
    public int insert(Enrollment enrollment) {
        if (checkById(enrollment)) return 2;
        String courseId = enrollment.getCourseId();
        String studentId = enrollment.getStudentId();

        // 查询一门课是否与该同学的其它课冲突
        Course course = courseMapper.checkById(courseId);
        String tim = course.getTim();
        List<Course> courses = checkCourse(studentId);
        for (Course item : courses) {
            String itemTim = item.getTim();
            if (conflict(tim, itemTim)) return 3;
        }

        // 判断课容量是否满了
        if (course.getSelectedCount() >= course.getCapacity()) return 4;
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

    public List<Course> checkCanChooseCourse(String studentId) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear() % 100;
        int currentMonth = currentDate.getMonthValue();
        if (currentMonth <= 6) {
            type = String.format("%02d", currentYear) + "0";
        } else {
            type = String.format("%02d", currentYear) + "1";
        }

        List<Course> courseList = courseMapper.checkAll();
        // 可选课程 满足专业，年级，学年
        List<Course> courses = new ArrayList<>();

        // 入学年份
        int year = Integer.parseInt(studentId.substring(0,2));
        // 年级
        int grade = Integer.parseInt(type.substring(0,2)) - year + 1;
        // 学年
        int semester = type.charAt(2) - '0';
        int major = Integer.parseInt(studentId.substring(3,5));

        for (Course item : courseList) {
            String courseId = item.getCourseId();
            int courseMajor = Integer.parseInt(courseId.substring(5,7));
            int courseGrade = courseId.charAt(7) - '0';
            int courseSemester = courseId.charAt(8) - '0';
            if (courseGrade == grade && courseSemester == semester && courseMajor == major) {
                item.setState(1);
                courses.add(item);
            }
        }

        // 已选课程
        List<Course> chooseCourses = checkCourse(studentId);

        for (Course chooseCourse : chooseCourses) {
            for (Course item : courses) {
                if (Objects.equals(item.getCourseId(), chooseCourse.getCourseId())) {
                    item.setState(2);
                } else if (conflict(chooseCourse.getTim(), item.getTim())) {
                    item.setState(3);
                } else if (item.getCapacity() == item.getSelectedCount()) {
                    item.setState(4);
                }
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



    public String checkTime() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear() % 100;
        int currentMonth = currentDate.getMonthValue();
        if (currentMonth <= 6) {
            type = String.format("%02d", currentYear) + "0";
        } else {
            type = String.format("%02d", currentYear) + "1";
        }
        return type;
    }

    public int setTime(String tim) {
        type = tim;
        return 1;
    }

    private boolean conflict(String tim1, String tim2) {
        if (tim1.charAt(1) == tim2.charAt(1)) {
            if (tim1.charAt(3) <= tim2.charAt(5) && tim1.charAt(3) >= tim2.charAt(3)) {
                return true;
            }
            if (tim1.charAt(5) <= tim2.charAt(5) && tim1.charAt(5) >= tim2.charAt(3)) {
                return true;
            }
        }
        return false;
    }
}
