package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    AdministratorMapper administratorMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    MessageMapper messageMapper;


    public int insert(Administrator administrator) {
        if (!isValidId(administrator.getId())) return 0;
        return administratorMapper.insert(administrator);
    }

    public int login(String id, String password) {
        if (null == administratorMapper.checkDTO(id, password)){
            return 0;
        } else {
            return 1;
        }
    }
    public boolean isValidId(String id) {
        if (id.startsWith("000")) {
            return true;
        }
        return false;
    }
    public int saveCourse(Course course) {
        course.setTeacherName(teacherMapper.checkById(course.getTeacherId()).getName());
        if (courseMapper.checkById(course.getCourseId()) == null) {
            return courseMapper.insert(course);
        } else {
            return courseMapper.update(course);
        }
    }
    public int deleteCourse(String id) {
        if (courseMapper.checkById(id) == null) return 0;
        return courseMapper.deleteById(id);
    }

    public int saveStudent(Student student) {
        if (studentMapper.checkById(student.getStudentId()) == null) {
            return studentMapper.insert(student);
        } else {
            return studentMapper.update(student);
        }
    }

    public int deleteStudent(String studentId) {
        if (studentMapper.checkById(studentId) == null) return 0;
        return studentMapper.removeById(studentId);
    }

    public int saveTeacher(Teacher teacher) {
        if (teacherMapper.checkById(teacher.getTeacherId()) == null) {
            return teacherMapper.insert(teacher);
        } else {
            return teacherMapper.update(teacher);
        }
    }

    public int deleteTeacher(String teacherId) {
        if (teacherMapper.checkById(teacherId) == null) return 0;
        return teacherMapper.removeById(teacherId);
    }

    public List<Message> checkMessage() {
        return messageMapper.checkFlagEquals0();
    }


    public String generateCourseId(String courseName,String major,String year, String term) {
        String courseNumber = generateCourseNumber(courseName);
        String courseCount = getCourseCount(courseName);
        // String term = getTerm();
        String courseId =courseNumber + courseCount + major + year + term;

        return courseId;
    }

//    public String generateCourseNumber(String courseName) {
//
//        if (courseMapper.checkAll().isEmpty()) {
//            return "000";
//        }
//
//        String courseType = courseMapper.getCourseTypeByName(courseName);
//        if (courseType != null) {
//            return courseType;
//        } else {
//            String maxCourseType = courseMapper.getMaxCourseType();
//            int nextCourseType = Integer.parseInt(maxCourseType) + 1;
//            return String.format("%03d", nextCourseType);
//        }
//    }
    public String generateCourseNumber(String courseName) {

        String courseType = courseMapper.getCourseTypeByName(courseName);
        if (courseType != null) {
            return courseType;
        }
        String maxCourseType = courseMapper.getMaxCourseType();
        if (null == maxCourseType) return "000";
        int nextCourseType = Integer.parseInt(maxCourseType) + 1;
        return String.format("%03d", nextCourseType);

    }
    public String getCourseCount(String courseName) {
        String maxCourseNumber = courseMapper.getMaxCourseNumber(courseName);
        if (maxCourseNumber == null) {
            return "00"; // 如果没有重名课程，则计数为 "00"
        } else {
            String count = maxCourseNumber.substring(3, 5);
            int nextCount = Integer.parseInt(count) + 1;
            return String.format("%02d", nextCount); // 计数加1，并格式化为两位数字
        }
    }

//    private String getTimeRule() {
//        LocalDate now = LocalDate.now();
//        int month = now.getMonthValue();
//
//        if (month <= 6) {
//            return "0";
//        } else if (month >= 9 && month <= 12) {
//            return "1";
//        } else {
//            // 默认情况，不在指定的时间段，返回空字符串或其他默认值
//            return ""; // 或者返回其他默认值
//        }
//    }

//    private String getTerm() {
//        LocalDate now = LocalDate.now();
//        int month = now.getMonthValue();
//        if (month <= 6) {
//            return "0";
//        }
//        return "1";
//    }

    public int accept(Message message)  {
//        int result = messageMapper.updateFlag(message.getId(), 1);
//        if (result == 0) return 0;
        message.setFlag(1);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setReceiveTime(sdf.format(currentDate));

        String major = message.getSendAccount().substring(3, 5);
        String year = message.getGrade();
        String term = message.getTerm();
        String courseId = generateCourseId(message.getCourseName(), major, year, term);
        message.setCourseId(courseId);

        if (messageMapper.update(message) == 0) return 0;

        //添加课程
        Course course = new Course();

        course.setCourseId(courseId);
        course.setName(message.getCourseName());
        course.setTeacherId(message.getSendAccount());
        course.setTeacherName(teacherMapper.checkById(message.getSendAccount()).getName());
        course.setTim(message.getTim());
        course.setRoom(message.getRoom());
        course.setCapacity(message.getCapacity());
        course.setCredits(message.getCredits());
        course.setSelectedCount(0);

        return courseMapper.insert(course);
    }
    public int deny(Message message) {
        // int result = messageMapper.updateFlag(message.getId(), 2);
        // if (result == 0) return 0;
        message.setFlag(2);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setReceiveTime(sdf.format(currentDate));

        return messageMapper.update(message);
    }

}
