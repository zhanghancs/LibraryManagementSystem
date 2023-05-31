package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Message;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/check")
    public List<Teacher> checkAll() {
        return teacherService.checkAll();
    }

    @GetMapping("/checkPage/{page}/{size}")
    public List<Teacher> findByPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return teacherService.checkByPage(page, size);
    }
    @GetMapping("/check/{teacherId}")
    public Teacher checkById(@PathVariable String teacherId) {
        return teacherService.checkById(teacherId);
    }

    @GetMapping("/checkCourse/{teacherId}")
    public List<Course> checkCourse(@PathVariable String teacherId) {
        return teacherService.checkCourse(teacherId);
    }
//    @PostMapping("/save")
//    public int save(@RequestBody Teacher teacher) {
//        return teacherService.save(teacher);
//    }
//
//    @DeleteMapping("/delete/{teacherId}")
//    public int delete(@PathVariable String teacherId) {
//        return teacherService.removeById(teacherId);
//    }
//    @PostMapping("/submitCourse")
//    public int submitCourse(@RequestParam("teacherId") String teacherId,
//                            @RequestParam("name") String name,
//                            @RequestParam("capacity") int capacity,
//                            @RequestParam("tim") String tim,
//                            @RequestParam("credits") int credits,
//                            @RequestParam("year") String year) {
//
//        Message message = new Message();
//        message.setSendAccount(teacherId);
//        message.setName(name);
//        message.setCapcity(capacity);
//        message.setCredits(credits);
//        message.setTim(tim);
//        message.setYear(year);
//
//        return teacherService.submitCourse(message);
//    }

    @PostMapping("/submitCourse")
    public int submitCourse(@RequestBody Message message) {
        message.setFlag(0);
        LocalDateTime now = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        message.setSendTime(now.format(formatter));
        return teacherService.submitCourse(message);
    }
    @GetMapping({"/checkSentMessage/{teacherId}"})
    public List<Message> checkSentMessage(@PathVariable String teacherId) {
        return this.teacherService.checkSentMessage(teacherId);
    }

//    @GetMapping({"/checkReceivedMessage/{teacherId}"})
//    public List<Message> checkReceivedMessage(@PathVariable String teacherId) {
//        return this.teacherService.checkReceivedMessage(teacherId);
//    }
}
