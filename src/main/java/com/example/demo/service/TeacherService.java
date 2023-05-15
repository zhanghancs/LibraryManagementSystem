package com.example.demo.service;

import com.example.demo.entity.Teacher;
import com.example.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    public List<Teacher> checkAll() {
        return  teacherMapper.checkAll();
    }

    public Teacher checkById(String teacherId) {
        if (!isValidId(teacherId)) return null;
        return teacherMapper.checkById(teacherId);
    }

    public boolean save(Teacher teacher) {
        if (teacherMapper.checkById(teacher.getTeacherId()) == null) {
            return teacherMapper.insert(teacher);
        } else {
            return teacherMapper.update(teacher);
        }
    }

    public boolean removeById(String teacherId) {
        if (teacherMapper.checkById(teacherId) == null) return false;
        return teacherMapper.removeById(teacherId);
    }

    public Integer login(String teacherId, String password) {
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
}
