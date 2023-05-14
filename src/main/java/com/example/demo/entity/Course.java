package com.example.demo.entity;

import lombok.Data;

@Data
public class Course {
    private String courseId;
    private String name;
    private String teacherId;
    private String tim; // 需要修改test
    private String room;
    private Integer capacity;
    private double credits;



}
