package com.example.demo.mapper;

import com.example.demo.entity.Administrator;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdministratorMapper {
    @Insert("insert into administrator(id, name, password, idNumber) " +
            "VALUES(#{id}, #{name},#{password},#{idNumber})")
    int insert(Administrator administrator);

    @Select("select * from administrator where id=#{id} and password=#{password}")
    Administrator checkDTO(String id, String password);

    @Delete("delete from administrator where id = #{adminId}")
    int deleteAdmin(String adminId);

    @Select("select * from administrator")
    List<Administrator> checkAll();
}
