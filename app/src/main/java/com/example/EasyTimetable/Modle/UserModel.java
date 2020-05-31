package com.example.EasyTimetable.Modle;

import android.util.Log;

import com.example.EasyTimetable.Interface.IUserBiz;
import com.example.EasyTimetable.Interface.ListenerInterface.OnLoginListener;
import com.example.EasyTimetable.Interface.ListenerInterface.UserPsdChangeListener;
import com.example.EasyTimetable.View.LoginActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class UserModel implements IUserBiz {
    /*
     * 登录
     * */
    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {
        //模拟网络请求耗时操作
        new Thread() {
            @Override
            public void run() {
                final User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User bmobUser, BmobException e) {
                        if (e == null) {
                            User user = BmobUser.getCurrentUser(User.class);
                            loginListener.loginSuccess(user);
                        } else {
                            loginListener.loginFailed(e);

                        }
                    }
                });
            }
        }.start();
    }
    /*修改密码*/
    @Override
    public void changePsd(String oldpassword, String newpassword, final UserPsdChangeListener userPsdChangeListener) {
        BmobUser.updateCurrentUserPassword(oldpassword.toString(), newpassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    userPsdChangeListener.success();
                } else {
                    userPsdChangeListener.failed();
                }
            }
        });
    }
}
