package com.example.demo.mapper;

import com.example.demo.entity.Enrollment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnrollmentMapper {
    @Insert("insert into enrollment(courseId, studentId) " +
            "VALUES(#{courseId},#{studentId})")
    int insert(Enrollment enrollment);

    @Select("select * from enrollment where studentId = #{studentId}")
    List<Enrollment> checkCourse(String studentId);

    @Select("select * from enrollment where courseId = #{courseId}")
    List<Enrollment> checkStudent(String courseId);

    @Delete("delete from enrollment where courseId = #{courseId} and studentId = #{studentId}")
    int deleteOne(Enrollment enrollment);

    @Select("select * from enrollment where courseId = #{courseId} and studentId = #{studentId}")
    Enrollment check(Enrollment enrollment);

    @Delete("delete from enrollment where courseId = #{courseId}")
    int deleteCourse(String courseId);

    @Delete("delete from enrollment where studentId = #{studentId}")
    int deleteStudent(String studentId);
}
