package com.example.demo.mapper;


import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student where studentId=#{studentId} and password=#{password}")
    Student checkDTO(String studentId, String password);

    @Select("select * from student")
    List<Student> checkAll();

    @Select("select * from student where studentId = #{studentId}")
    Student checkById(String studentId);

    int update(Student student);

    @Insert("insert into student(studentId,name,password,idNumber,selectedCredits,earnedCredits) " +
            "VALUES(#{studentId}, #{name},#{password},#{idNumber},#{selectedCredits},#{earnedCredits})")
    int insert(Student student);

    @Delete("delete from student where studentId = #{studentId} ")
    int removeById(String studentId);

    @Update("update student set selectedCredits=selectedCredits+#{num} where studentId = #{studentId}")
    int increaseCredits(String studentId, double num);

    @Update("update student set selectedCredits=selectedCredits-#{num} where studentId = #{studentId}")
    int reduceCredits(String studentId, double num);
}
