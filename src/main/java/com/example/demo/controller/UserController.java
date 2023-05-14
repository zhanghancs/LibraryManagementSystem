package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Integer login(@RequestBody UserDTO userDTO) {
        String id = userDTO.getId();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(id) || StrUtil.isBlank(password)) {
            return 0;
        }
        return userService.login(id,password);
    }
}
