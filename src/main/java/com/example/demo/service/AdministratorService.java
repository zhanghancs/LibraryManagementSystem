package com.example.demo.service;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.AdministratorMapper;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    AdministratorMapper administratorMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    StudentMapper studentMapper;

    public int insert(Administrator administrator) {
        if (!isValidId(administrator.getId())) return 0;
        return administratorMapper.insert(administrator);
    }

    public int login(String id, String password) {
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
    public int saveCourse(Course course) {
        course.setTeacherName(teacherMapper.checkById(course.getTeacherId()).getName());
        if (courseMapper.checkByCourseId(course.getCourseId()) == null) {
            return courseMapper.insert(course);
        } else {
            return courseMapper.update(course);
        }
    }
    public int deleteCourse(String id) {
        if (courseMapper.checkByCourseId(id) == null) return 0;
        return courseMapper.deleteById(id);
    }

    public int saveStudent(Student student) {
        if (studentMapper.checkById(student.getStudentId()) == null) {
            return studentMapper.insert(student);
        } else {
            return studentMapper.update(student);
        }
    }

    public int deleteStudent(String studentId) {
        if (studentMapper.checkById(studentId) == null) return 0;
        return studentMapper.removeById(studentId);
    }

    public int saveTeacher(Teacher teacher) {
        if (teacherMapper.checkById(teacher.getTeacherId()) == null) {
            return teacherMapper.insert(teacher);
        } else {
            return teacherMapper.update(teacher);
        }
    }

    public int deleteTeacher(String teacherId) {
        if (teacherMapper.checkById(teacherId) == null) return 0;
        return teacherMapper.removeById(teacherId);
    }

}
