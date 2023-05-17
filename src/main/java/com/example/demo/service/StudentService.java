package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public List<Student> checkAll() {
        return  studentMapper.checkAll();
    }

    public Student checkById(String studentId) {
        if (!isValidId(studentId)) return null;
        return studentMapper.checkById(studentId);
    }

    public int login(String studentId, String password) {
        if (null == studentMapper.checkDTO(studentId, password)) {
            return 0;
        } else {
            return 3;
        }
    }

    public List<Student> checkByPage(Integer page, Integer size) {
        List<Student> studentList = studentMapper.checkAll();
        ArrayList<Student> list = new ArrayList<>();
        int sz = studentList.size();
        int start = size * page;
        int end = size * (page + 1) < sz ? size*(page + 1) : sz;
        for (int i = start; i < end; i++) {
            list.add(studentList.get(i));
        }
        return list;
    }

    public boolean isValidId(String id) {
        if (id.startsWith("000") || id.startsWith("001")) {
            return false;
        }
        return true;
    }
}
