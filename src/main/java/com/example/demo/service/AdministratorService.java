package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.dto.AdministratorList;
import com.example.demo.entity.dto.StudentList;
import com.example.demo.entity.dto.TeacherList;
import com.example.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    EnrollmentMapper enrollmentMapper;

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
        enrollmentMapper.deleteCourse(id);
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
        enrollmentMapper.deleteStudent(studentId);
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
        messageMapper.deleteByTeacherId(teacherId);
        List<Course> courses = courseMapper.checkByTeacherId(teacherId);
        for (Course course : courses) {
            deleteCourse(course.getCourseId());
        }
        return teacherMapper.removeById(teacherId);
    }

    public int saveTeachers(List<Teacher> teacherList) {
        int ans = 0;
        for (Teacher teacher : teacherList) {
            ans += 1;
            if ( 0 == saveTeacher(teacher)) return 0;
        }
        return ans;
    }

    public int saveStudents(List<Student> studentList) {
        int ans = 0;
        for (Student student : studentList) {
            ans += 1;
            if ( 0 == saveStudent(student)) return 0;
        }
        return ans;
    }

    // 查询所有的未审核信息
    public List<Message> checkMessage() {
        List<Message> messages = messageMapper.checkFlagEquals0();
        for (Message message : messages) {
            message.setState(0);
            if (checkTimeConflict(message.getSendAccount(), message.getTim())) {
                message.setState(1);
            }
        }
        return messages;
    }

    public List<Message> checkAllMessage() {
        List<Message> messages = messageMapper.checkAll();
        for (Message message : messages) {
            if (message.getFlag() == 0) {
                message.setState(0);
                if (checkTimeConflict(message.getSendAccount(), message.getTim())) {
                    message.setState(1);
                }
            }
        }
        return messages;
    }

    public List<String> checkFreeRooms(String tim, String start) {
        List<String> rooms = generateRooms(start);
        List<Course> courses = courseMapper.checkAll();
        for (Course course : courses) {
            if(conflict(tim, course.getTim())) {
                rooms.removeIf(room -> room.equals(course.getRoom()));
            }
        }
        return rooms;
    }
    public int accept(Message message)  {
//        int result = messageMapper.updateFlag(message.getId(), 1);
//        if (result == 0) return 0;
        message.setFlag(1);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setReceiveTime(sdf.format(currentDate));

        String major = message.getSendAccount().substring(3, 5);
        String grade = message.getGrade();
        // String term = message.getTerm();
        String courseId = generateCourseId(message.getCourseName(), major, grade);
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

    private List<String> generateRooms(String start) {
        List<String> rooms = new ArrayList<>();
        for (int i=1; i<=8; ++i) {
            for (int j=1; j<=5; ++j) {
                for (int k=0; k<=6; ++k) {
                    rooms.add("j"+i+"-"+j+"0"+k);
                }
            }
        }
        if (start != null) {
            rooms.removeIf(room -> !room.startsWith(start));
        }
        return rooms;
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
    private String generateCourseId(String courseName,String major,String grade) {
        String courseNumber = generateCourseNumber(courseName);
        String courseCount = getCourseCount(courseName);
        String term = getTerm();
        String courseId =courseNumber + courseCount + major + grade + term;

        return courseId;
    }
    private String generateCourseNumber(String courseName) {

        String courseType = courseMapper.getCourseTypeByName(courseName);
        if (courseType != null) {
            return courseType;
        }
        String maxCourseType = courseMapper.getMaxCourseType();
        if (null == maxCourseType) return "000";
        int nextCourseType = Integer.parseInt(maxCourseType) + 1;
        return String.format("%03d", nextCourseType);

    }
    private String getCourseCount(String courseName) {
        String maxCourseNumber = courseMapper.getMaxCourseNumber(courseName);
        if (maxCourseNumber == null) {
            return "00"; // 如果没有重名课程，则计数为 "00"
        } else {
            String count = maxCourseNumber.substring(3, 5);
            int nextCount = Integer.parseInt(count) + 1;
            return String.format("%02d", nextCount); // 计数加1，并格式化为两位数字
        }
    }
    private String getTerm() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        if (month <= 6) {
            return "0";
        }
        return "1";
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



    // 查询 老师 tim 内是否有课
    private boolean checkTimeConflict(String teacherId, String tim) {
        List<Course> courses = courseMapper.checkByTeacherId(teacherId);
        for (Course item : courses) {
            if (conflict(item.getTim(), tim)) {
                return true;
            }
        }
        return false;
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

    public int saveAdmins(List<Administrator> administratorList) {
        int ans = 0;
        for (Administrator administrator : administratorList) {
            ans += insert(administrator);
        }
        return ans;
    }

    public int deleteAdmin(String adminId) {
        return administratorMapper.deleteAdmin(adminId);
    }


//    public List<Message> checkAllMessage() {
//        return messageMapper.checkAll();
//    }




}
