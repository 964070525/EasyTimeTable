package com.example.EasyTimetable.Interface.ListenerInterface;
import com.example.EasyTimetable.Modle.User;

import cn.bmob.v3.exception.BmobException;

public interface OnLoginListener {
    //登录成功的回调
    void loginSuccess(User user);
    //登录失败的回调
    void loginFailed(BmobException e);
}