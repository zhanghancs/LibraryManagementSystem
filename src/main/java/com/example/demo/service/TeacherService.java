package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Message;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public int submitCourse(Message message) {
        int rowsAffected = messageMapper.teacherInsert(message);
        return rowsAffected > 0 ? 1 : 0;
    }
    //查看消息
    public List<Message> checkSentMessage(String sendAccount)
    {
        return this.messageMapper.checkBySender(sendAccount);
    }

    public List<Message> checkReceivedMessage(String receiveAccount)
    {
        return this.messageMapper.checkByReceiver(receiveAccount);
    }
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
