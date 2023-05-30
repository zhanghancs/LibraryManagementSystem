package com.example.demo.entity;


import java.text.SimpleDateFormat;
import java.util.Date;

    public class Message {
        private String sendAccount;
        private String receiveAccount;
        private int flag;
        private String courseId;
        private String name;
        private int capcity;
        private float credits;
        private String tim;
        private String room;
        private String textTim;
        private String year;
        public Message()
        {
            this.courseId="";
            this.flag = 0;
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = sdf.format(currentDate);
            this.textTim = dateString;
            this.room="";
        }
        //发送者、消息类型（int变量）、课程编号、课程名称、课容量、课程学分、开始时间，结束时间，消息发送时间


        public String getSendAccount() {
            return sendAccount;
        }

        public void setSendAccount(String sendAccount) {
            this.sendAccount = sendAccount;
        }

        public String getReceiveAccount() {
            return receiveAccount;
        }

        public void setReceiveAccount(String receiveAccount) {
            this.receiveAccount = receiveAccount;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCapcity() {
            return capcity;
        }

        public void setCapcity(int capcity) {
            this.capcity = capcity;
        }

        public float getCredits() {
            return credits;
        }

        public void setCredits(float credits) {
            this.credits = credits;
        }

        public String getTim() {
            return tim;
        }

        public void setTim(String tim) {
            this.tim = tim;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getTextTim() {
            return textTim;
        }

        public void setTextTim(String textTim) {
            this.textTim = textTim;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }

//教师申请开课的时候要提交：课程名字，教师名字，学分，上课时间，消息标记设为0表示请求

