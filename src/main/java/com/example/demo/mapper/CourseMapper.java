package com.example.demo.mapper;

import com.example.demo.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("select * from course")
    List<Course> checkAll();

    @Select("select * from course where courseId = #{courseId}")
    Course checkByCourseId(String courseId);

    boolean update(Course course);

    @Insert("insert into course(courseId,name,teacherId,tim,room,capacity,credits) " +
            "VALUES(#{courseId},#{name},#{teacherId},#{tim},#{room},#{capacity},#{credits})")
    boolean insert(Course course);

    @Delete("delete from course where courseId = #{courseId} ")
    boolean removeById(String courseId);
}
