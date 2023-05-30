package com.example.demo.mapper;

import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select({"select * from messageRecord where sendAccount=#{sendAccount}"})
    List<Message> checkBySender(String sendAccount);

    @Select({"select * from messageRecord where receiveAccount=#{receiveAccount}"})
    List<Message> checkByReceiver(String receiveAccount);

    @Select({"select * from messageRecord"})
    List<Message> checkAll();

    @Select({"select * from messageRecord where flag=0"})
    List<Message> checkFlagEquals0();


    //更新flag，这里要确保传递的message属性和数据库里的记录完全一致，即前端发回的是原消息，才能修改
    @Update({"update messageRecord set flag = #{flag} where sendAccount = #{sendAccount} and name = #{name} and capcity = #{capcity} and credits = #{credits} and tim = #{tim}"})
    int updateFlag(Message message, int flag);

    @Insert({"insert into messageRecord(sendAccount,name,capcity,credits,tim,textTim,year) VALUES(#{sendAccount}, #{name},#{capcity},#{credits},#{tim},#{textTim},#{year})"})
    int teacherInsert(Message message);

   @Insert({"insert into messageRecord(sendAccount,receiveAccount,flag,courseId,name,capcity,credits,tim,room,textTim,year) " +
           "VALUES(#{sendAccount}, #{receiveAccount},#{flag},#{courseId},#{name},#{capcity},#{credits},#{tim},#{room},#{textTim},#{year})"})
    int adminInsert(Message message);


}
