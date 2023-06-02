package com.example.demo.entity.dto;

import com.example.demo.entity.Administrator;
import lombok.Data;

import java.util.List;

@Data
public class AdministratorList {
    private List<Administrator> administratorList;
}
