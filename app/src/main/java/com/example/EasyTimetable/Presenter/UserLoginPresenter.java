package com.example.EasyTimetable.Presenter;

import android.os.Handler;
import android.util.Log;

import com.example.EasyTimetable.Interface.IUserBiz;
import com.example.EasyTimetable.Interface.ListenerInterface.OnLoginListener;
import com.example.EasyTimetable.Interface.ViewInterface.IUserLoginView;
import com.example.EasyTimetable.Modle.User;
import com.example.EasyTimetable.Modle.UserModel;

import cn.bmob.v3.exception.BmobException;


public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    //对应视图页面销毁的标志位,当视图销毁后回调就不需要处理了
    private boolean destroyFlag;

    //Presenter拿到View和Model的实现类
    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserModel();
    }

    public void login() {
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                if (!destroyFlag) { //View层销毁后不需要处理的判断
                    //需要在UI线程执行
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            userLoginView.toMainActivity(user);
                        }
                    });
                }
            }
            @Override
            public void loginFailed(final BmobException e) {
                if (!destroyFlag) { //View层销毁后不需要处理的判断
                    //需要在UI线程执行
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            userLoginView.showFaileTips();
                            Log.e("LoginActivity",e.toString());
                        }
                    });
                }
            }
        });
    }

    //解绑视图
    public void detachView() {
        destroyFlag = true;
        this.userLoginView = null;
    }
}