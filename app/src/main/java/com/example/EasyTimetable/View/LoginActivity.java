package com.example.EasyTimetable.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.EasyTimetable.Interface.ViewInterface.IUserLoginView;
import com.example.EasyTimetable.Modle.User;
import com.example.EasyTimetable.Presenter.UserLoginPresenter;
import com.example.EasyTimetable.R;

import cn.bmob.v3.Bmob;

public class LoginActivity extends AppCompatActivity implements IUserLoginView {
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin;
    private UserLoginPresenter userLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        userLoginPresenter = new UserLoginPresenter(this);
        Bmob.initialize(this, "628aac1cbf89bdb84328f3acd41acd33");
        /*
         * 登录
         * */
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginPresenter.login();
            }
        });
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void toMainActivity(User user) {
        Intent intent;
        if (user.getUsername().equals("a")) {
            intent = new Intent(LoginActivity.this, AdminActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, UserActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void showFaileTips() {
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        mEtUsername.setText("");
        mEtPassword.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //为了防止内存泄漏，解绑Presenter层对View层的引用
        userLoginPresenter.detachView();
    }

}