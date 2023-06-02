package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Message;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    MessageMapper messageMapper;

    public List<Teacher> checkAll() {
        return  teacherMapper.checkAll();
    }

    public Teacher checkById(String teacherId) {
        if (!isValidId(teacherId)) return null;
        return teacherMapper.checkById(teacherId);
    }

    public int login(String teacherId, String password) {
        if (null == teacherMapper.checkDTO(teacherId, password)) {
            return 0;
        }
        else {
            return 2;
        }
    }

    public List<Teacher> checkByPage(Integer page, Integer size) {
        List<Teacher> teacherList = teacherMapper.checkAll();
        ArrayList<Teacher> list = new ArrayList<Teacher>();
        int sz = teacherList.size();

        int start = size * page;
        int end = size * (page + 1) < sz ? size*(page + 1) : sz;
        for (int i = start; i < end; i++) {
            list.add(teacherList.get(i));
        }
        return list;
    }
    public boolean isValidId(String id) {
        if (id.startsWith("001")) {
            return true;
        }
        return false;
    }

    public List<Course> checkCourse(String teacherId) {
        return courseMapper.checkByTeacherId(teacherId);
    }

    //申请开课
//    public int submitCourse(String senderAccount, String name, String capacity, String tim, String credits) {
//        Message message = new Message();
//        message.setSendAccount(senderAccount);
//        message.setName(name);
//        message.setCapcity(capacity);
//        message.setTim(tim);
//        message.setCredits(credits);
//
//        int rowsAffected = messageMapper.teacherInsert(message);
//
//        return rowsAffected > 0 ? 1 : 0;
//    }
    private String getNewTerm() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        if (month <= 6) {
            return "0";
        }
        return "1";
    }
    public int submitCourse(Message message) {
        message.setFlag(0);
        LocalDateTime now = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        message.setSendTime(now.format(formatter));
        message.setTerm(getNewTerm());
        message.setTeacherName(teacherMapper.checkById(message.getSendAccount()).getName());
        return messageMapper.insert(message);
    }

    //查看消息
    public List<Message> checkSentMessage(String sendAccount) {
        return messageMapper.checkBySender(sendAccount);
    }

    public List<String> checkFreeTime(String teacherId, String start) {
        List<String> times = generateTimes(start);
        List<Course> courses = checkCourse(teacherId);
        for (Course course : courses) {
            times.removeIf(tim -> conflict(tim, course.getTim()));
        }
        return times;
    }
    private List<String> generateTimes(String start) {
        List<String> week = new ArrayList<>(Arrays.asList("周一", "周二", "周三", "周四", "周五"));
        List<String> tim = new ArrayList<>(Arrays.asList("1-2", "2-4", "3-4", "3-5", "6-7", "8-9", "6-8"));
        List<String> result = new ArrayList<>();
        for (String w : week) {
            for (String t : tim) {
                String now = w + " " + t;
                if (now.startsWith(start)) result.add(now);
            }
        }
        return result;
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
//    public List<Message> checkReceivedMessage(String receiveAccount) {
//        return messageMapper.checkByReceiver(receiveAccount);
//    }


//    public int save(Teacher teacher) {
//        if (teacherMapper.checkById(teacher.getTeacherId()) == null) {
//            return teacherMapper.insert(teacher);
//        } else {
//            return teacherMapper.update(teacher);
//        }
//    }

//    public int removeById(String teacherId) {
//        if (teacherMapper.checkById(teacherId) == null) return 0;
//        return teacherMapper.removeById(teacherId);
//    }


}
