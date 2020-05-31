package com.example.EasyTimetable.View;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.EasyTimetable.R;

import cn.bmob.v3.Bmob;

public class UserActivity extends AppCompatActivity {
    private BottomNavigationBar mBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.useractivity);
        Bmob.initialize(this, "628aac1cbf89bdb84328f3acd41acd33");
        initView();
        setView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
    }

    /**
     * 配置控件
     */
    private void setView() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setActiveColor(R.color.colorPrimaryDark);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.item2_after, "排课")
                // .setInactiveIcon(ContextCompat.getDrawable(UserActivity.this, R.drawable.item2_before))
        )
                .addItem(new BottomNavigationItem(R.drawable.item3_after, "我的"))
                //   .setInactiveIcon(ContextCompat.getDrawable(UserActivity.this, R.drawable.item3_before)))
                .setFirstSelectedPosition(1)//设置第一个菜单为选中状态
                .initialise();//必须调用该方法，才会生效

        //BottomNavigationBar选项卡，选择事件
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                switch (position) {
                    case 0:
                        replace(new TeacherSchedulingFragment());
                        break;
                    case 1:
                        replace(new TeacherInformationFragment());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });
        //设置第一个要显示的Fragment
        replace(new TeacherInformationFragment());
    }

    /**
     * 切换Fragment
     *
     * @param fragment Fragment
     */
    private void replace(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frameContent, fragment);
        transaction.commit();
    }
}