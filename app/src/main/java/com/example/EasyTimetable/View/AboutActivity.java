package com.example.EasyTimetable.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.EasyTimetable.Interface.ViewInterface.AboutView;
import com.example.EasyTimetable.Presenter.AboutPresenter;
import com.example.EasyTimetable.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class AboutActivity extends AppCompatActivity implements AboutView {
    private TextView contentView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private CollapsingToolbarLayout layout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        contentView = (TextView) findViewById(R.id.cat_text_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {//启用 HomeAsUp 按钮（返回箭头）
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置标题
        layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        layout.setTitle("易排课");
        imageView = (ImageView) findViewById(R.id.cat_image_view);
        Glide.with(this).load(R.drawable.ypkbar2).into(imageView);//加载图片
        AboutPresenter aboutPresenter = new AboutPresenter(this);
        aboutPresenter.init();

    }

    @Override
    public void setContentView(String string) {
        contentView.setText(string);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://点击 HomeAsUp 按钮时
                finish();//关闭当前活动（即返回上一个活动）
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}