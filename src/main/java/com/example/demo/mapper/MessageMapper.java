package com.example.demo.mapper;

import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select * from messageRecord where sendAccount=#{sendAccount}")
    List<Message> checkBySender(String sendAccount);

    @Select("select * from messageRecord where receiveAccount=#{receiveAccount}")
    List<Message> checkByReceiver(String receiveAccount);

    @Select("select * from messageRecord")
    List<Message> checkAll();

    @Select("select * from message where flag=0")
    List<Message> checkFlagEquals0();


    //更新flag，这里要确保传递的message属性和数据库里的记录完全一致，即前端发回的是原消息，才能修改
//    @Update("update messageRecord set flag = #{flag} where sendAccount = #{sendAccount} " +
//            "and name = #{name} and capcity = #{capcity} and credits = #{credits} and tim = #{tim}")
//    int updateFlag(Message message, int flag);

    @Update("update message set flag = #{flag} where id = #{id}")
    int updateFlag(int id, int flag);

//    @Insert("insert into messageRecord(sendAccount,courseName,year,capacity,credits,tim,textTim) " +
//            "VALUES(#{sendAccount}, #{courseName},#{year},#{capacity},#{credits},#{tim},#{textTim})")
//    int teacherInsert(Message message);
//
//   @Insert("insert into messageRecord(sendAccount,receiveAccount,flag,courseId,courseName,year," +
//           "capcity,credits,tim,room,textTim) VALUES(#{sendAccount}, #{receiveAccount}," +
//           "#{flag},#{courseId},#{courseName},#{year},#{capcity},#{credits},#{tim},#{room},#{textTim})")
//    int adminInsert(Message message);

    @Insert("insert into message(sendAccount,receiveAccount,flag,courseId,courseName,grade,term," +
            "capacity,credits,tim,room,sendTime,receiveTime) VALUES(#{sendAccount}, #{receiveAccount},#{flag}," +
            "#{courseId},#{courseName},#{grade},#{term},#{capacity},#{credits},#{tim},#{room},#{sendTime},#{receiveTime})")
    int insert(Message message);

    @Update("UPDATE message SET receiveAccount = #{receiveAccount}, courseId = #{courseId}, room = #{room}," +
            " receiveTime = #{receiveTime}, flag = #{flag} WHERE id = #{id}")
    int update(Message message);

}
