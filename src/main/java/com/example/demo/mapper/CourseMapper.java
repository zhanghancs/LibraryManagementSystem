package com.example.demo.mapper;

import com.example.demo.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("select * from course")
    List<Course> checkAll();

    @Select("select * from course where courseId = #{courseId}")
    Course checkByCourseId(String courseId);

    int update(Course course);

    @Insert("insert into course(courseId,name,teacherId,teacherName,tim,room,capacity,credits,selectedCount) " +
            "VALUES(#{courseId},#{name},#{teacherId},#{teacherName},#{tim},#{room},#{capacity},#{credits},#{selectedCount})")
    int insert(Course course);

    @Delete("delete from course where courseId = #{courseId} ")
    int deleteById(String courseId);

    @Update("update course set selectedCount=selectedCount-1 where courseId = #{courseId}")
    int reduceSelectedCount(String courseId);

    @Update("update course set selectedCount=selectedCount+1 where courseId = #{courseId}")
    int increaseSelectedCount(String courseId);

}
