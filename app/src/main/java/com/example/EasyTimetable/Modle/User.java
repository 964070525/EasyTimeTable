package com.example.EasyTimetable.Modle;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 头像
     */
    private BmobFile avatar;
    private String college;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getAge() {
        return age;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", avatar=" + avatar +
                '}';
    }
}