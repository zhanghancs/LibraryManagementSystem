package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.EnrollmentMapper;
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
        return courseMapper.checkById(id);
    }

    public List<Course> findCourses(Course course) {
        List<Course> courses = checkAll();
        ArrayList<Course> courseList = new ArrayList<Course>();
        String courseId = course.getCourseId();
        String number = "*****";
        String major = "**";
        char grade = '*';
        char semester = '*';

        if (courseId != null) {
            // 编号
            number = courseId.substring(0, 5);
            // 专业
            major = courseId.substring(5, 7);
            // 年级
            grade = courseId.charAt(7);
            // 学年
            semester = courseId.charAt(8);
        }
        String tim = course.getTim();
        char week = '*';
        char start = '*';
        char end = '*';
        if (tim != null) {
            week = tim.charAt(1);
            start = tim.charAt(3);
            end = tim.charAt(5);
        }
        String name = course.getName();
        String teacherId = course.getTeacherId();
        String teacherName = course.getTeacherName();
        String room = course.getRoom();
        int capacity = course.getCapacity();
        double credits = course.getCredits();
        int selectedCount = course.getSelectedCount();

        for (Course item : courses) {
            boolean flag = true;
            String itemId = item.getCourseId();
            String itemNumber = itemId.substring(0, 5);
            String itemMajor = itemId.substring(5, 7);
            char itemGrade = itemId.charAt(7);
            char itemSemester = itemId.charAt(8);
            String itemTim = item.getTim();
            char itemWeek = itemTim.charAt(1);
            char itemStart = itemTim.charAt(3);
            char itemEnd = itemTim.charAt(5);
            if (!number.equals("*****") && !number.equals(itemNumber)) {
                flag = false;
            }
            if (!major.equals("**") && !major.equals(itemMajor)) {
                flag = false;
            }
            if (grade != '*' && grade != itemGrade) {
                flag = false;
            }
            if (semester != '*' && semester != itemSemester) {
                flag = false;
            }
            if (name != null && !name.equals(item.getName())) {
                flag = false;
            }
            if (teacherId != null && !teacherId.equals(item.getTeacherId())) {
                flag = false;
            }

            if (teacherName != null && !teacherName.equals(item.getTeacherName())) {
                flag = false;
            }
//            if (tim != null && !tim.equals(item.getTim())) {
//                flag = false;
//            }
            if (week != '*' && week != itemWeek) {
                flag = false;
            }
            if (start != '*' && start != itemStart) {
                flag = false;
            }
            if (end != '*' && end != itemEnd) {
                flag = false;
            }
            if (room != null && !room.equals(item.getRoom())) {
                flag = false;
            }
            if (capacity != 0 && capacity != item.getCapacity()) {
                flag = false;
            }
            if (credits != 0 && credits != item.getCredits()) {
                flag = false;
            }
            if (selectedCount != 0 && selectedCount != item.getSelectedCount()) {
                flag = false;
            }

            if (flag) courseList.add(item);
        }
        return courseList;
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

    public int checkCourseCount(String courseId) {
        return checkById(courseId).getSelectedCount();
    }



//    public int save(Course course) {
//        course.setTeacherName(teacher);
//        if (courseMapper.checkByCourseId(course.getCourseId()) == null) {
//            return courseMapper.insert(course);
//        } else {
//            return courseMapper.update(course);
//        }
//    }

//    public int removeById(String id) {
//        if (courseMapper.checkByCourseId(id) == null) return 0;
//        return courseMapper.removeById(id);
//    }


}
