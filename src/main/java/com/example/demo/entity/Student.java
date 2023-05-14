package com.example.demo.entity;

import lombok.Data;


@Data
public class Student {
    private String studentId;
    private String name;
    private String password;
    private String idNumber;
    private double chosenCredits;
    private double earnedCredits;
}
