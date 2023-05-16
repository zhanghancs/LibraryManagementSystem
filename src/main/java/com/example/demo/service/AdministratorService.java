package com.example.demo.service;

import com.example.demo.entity.Administrator;
import com.example.demo.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    AdministratorMapper administratorMapper;
    public boolean insert(Administrator administrator) {
        if (!isValidId(administrator.getId())) return false;
        return administratorMapper.insert(administrator);
    }

    public Integer login(String id, String password) {
        if (null == administratorMapper.checkDTO(id, password)){
            return 0;
        } else {
            return 1;
        }
    }
    public boolean isValidId(String id) {
        if (id.startsWith("000")) {
            return true;
        }
        return false;
    }
}
