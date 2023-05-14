package com.example.demo.mapper;

import com.example.demo.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface TeacherMapper {
    @Select("select * from teacher where teacherId=#{teacherId} and password=#{password}")
    Teacher checkDTO(String teacherId, String password);

    @Select("select * from teacher")
    List<Teacher> checkAll();

    @Select("select * from teacher where teacherid = #{teacherId}")
    Teacher checkById(String teacherId);

    boolean update(Teacher teacher);

    @Insert("insert into teacher(teacherId,name,password,idNumber) " +
            "VALUES(#{teacherId}, #{name},#{password},#{idNumber})")
    boolean insert(Teacher teacher);

    @Delete("delete from teacher where teacherId = #{teacherId} ")
    boolean removeById(String teacherId);
}
