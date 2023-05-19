package com.example.demo.entity;

import lombok.Data;


@Data
public class Student {
    private String studentId;
    private String name;
    private String password;
    private String idNumber;
    // 已选择的学分
    private double selectedCredits;
    // 已获得的学分
    private double earnedCredits;
}
