package com.example.EasyTimetable.Interface.ViewInterface;
import com.example.EasyTimetable.Modle.User;
public interface IUserLoginView {
    /*获取用户名*/
    String getUserName();
    /*获取密码*/
    String getPassword();
    /*登录成功*/
    void toMainActivity(User user);
    /*登录失败*/
    void showFaileTips();
}



