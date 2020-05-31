package com.example.EasyTimetable.Interface;

import com.example.EasyTimetable.Interface.ListenerInterface.OnLoginListener;
import com.example.EasyTimetable.Interface.ListenerInterface.UserPsdChangeListener;

public interface IUserBiz {
    //登录方法
    public void login(String username, String password, OnLoginListener loginListener);
    //修改密码方法
    public void changePsd(String oldpassword, String newpassword, UserPsdChangeListener userPsdChangeListener);
}