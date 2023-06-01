package com.example.demo.entity.dto;

import com.example.demo.entity.Student;
import lombok.Data;

import java.util.List;

@Data
public class StudentList {
    private List<Student> studentList;
}
