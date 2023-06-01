package com.example.demo.entity.dto;

import com.example.demo.entity.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class TeacherList {
    private List<Teacher> teacherList;
}
