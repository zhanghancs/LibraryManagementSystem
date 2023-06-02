package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.entity.dto.StudentList;
import com.example.demo.entity.dto.TeacherList;
import com.example.demo.entity.dto.AdministratorList;
import com.example.demo.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    @PostMapping("/insertAdmin")
    public int insert(@RequestBody Administrator administrator) {
        return administratorService.insert(administrator);
    }

    @DeleteMapping("/deleteAdmin/{adminId}")
    public int deleteAdmin(@PathVariable String adminId) { return administratorService.deleteAdmin(adminId);}

    @PostMapping("/saveAdmins")
    public int saveAdmins(@RequestBody List<Administrator> administratorList) {
        return administratorService.saveAdmins(administratorList);
    }
    @GetMapping("/checkAll")
    public List<Administrator> checkAll() {
        return administratorService.checkAll();
    }

    @PostMapping("/saveCourse")
    public int saveCourse(@RequestBody Course course) {
        return administratorService.saveCourse(course);
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public int deleteCourse(@PathVariable String courseId) {
        return administratorService.deleteCourse(courseId);
    }

    @PostMapping("/saveStudent")
    public int saveStudent(@RequestBody Student student) {
        return administratorService.saveStudent(student);
    }

//    @PostMapping("/saveStudents")
//    public int saveStudents(@RequestBody StudentList studentList) {
//        return administratorService.saveStudents(studentList);
//    }
    @PostMapping("/saveStudents")
    public int saveStudents(@RequestBody List<Student> studentList) {
        return administratorService.saveStudents(studentList);
    }
    @DeleteMapping("/deleteStudent/{studentId}")
    public int deleteStudent(@PathVariable String studentId) {
        return administratorService.deleteStudent(studentId);
    }

    @PostMapping("/saveTeacher")
    public int saveTeacher(@RequestBody Teacher teacher) {
        return administratorService.saveTeacher(teacher);
    }

    @PostMapping("/saveTeachers")
    public int saveTeachers(@RequestBody List<Teacher> teacherList) {
        return administratorService.saveTeachers(teacherList);
    }

    @DeleteMapping("/deleteTeacher/{teacherId}")
    public int deleteTeacher(@PathVariable String teacherId) {
        return administratorService.deleteTeacher(teacherId);
    }

    //查询所有未审核的 message
    @GetMapping("/checkMessage")
    public List<Message> checkMessage() {
        return administratorService.checkMessage();
    }

    // 查询所有 message
    @GetMapping("/checkAllMessage")
    public List<Message> checkAllMessage() {
        return administratorService.checkAllMessage();
    }

    // 同意 申请
    @PostMapping("/acceptProposal")
    public int acceptProposal(@RequestBody Message message) {
        return administratorService.accept(message);
    }

    // 拒绝 申请
    @PostMapping("/denyProposal")
    public int denyProposal(@RequestBody Message message) {
        return administratorService.deny(message);
    }

    // 查询 所有 tim 时间内的空闲教室
    @GetMapping("/checkFreeRooms/{tim}/{start}")
    public List<String> checkFreeRooms(@PathVariable String tim, @PathVariable String start) {
        return administratorService.checkFreeRooms(tim, start);
    }
}
