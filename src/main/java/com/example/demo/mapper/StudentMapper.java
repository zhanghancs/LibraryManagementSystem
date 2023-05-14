package com.example.demo.mapper;


import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student where studentId=#{studentId} and password=#{password}")
    Student checkDTO(String studentId, String password);

    @Select("select * from student")
    List<Student> checkAll();

    @Select("select * from student where studentId = #{studentId}")
    Student checkById(String studentId);

    boolean update(Student student);

    @Insert("insert into student(studentId,name,password,idNumber,chosenCredits,earnedCredits) " +
            "VALUES(#{studentId}, #{name},#{password},#{idNumber},#{chosenCredits},#{earnedCredits})")
    boolean insert(Student student);

    @Delete("delete from student where studentId = #{studentId} ")
    boolean removeById(String studentId);


}
