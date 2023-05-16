package com.example.demo.mapper;

import com.example.demo.entity.Administrator;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdministratorMapper {
    @Insert("insert into administrator(id, name, passwrod, idNumber) " +
            "VALUES(#{studentId}, #{name},#{password},#{idNumber})")
    boolean insert(Administrator administrator);

    @Select("select * from administrator where id=#{id} and password=#{password}")
    Administrator checkDTO(String id, String password);
}
