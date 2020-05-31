package com.example.EasyTimetable.Presenter;

import com.example.EasyTimetable.Interface.ViewInterface.AboutView;

public class AboutPresenter {
    private AboutView aboutView;

    public AboutPresenter(AboutView aboutView) {
        this.aboutView = aboutView;
    }
    public void init(){
        String string = "   易排课（Easy Timetable），是一款专门用于高校机房排课的APP。高校机房排课是高校实验教学运行管理中非常重要的环节，它涉及面广、限制条件多。科学合理地编排课程表， 对保持稳定的实验教学秩序、 确保实验教学任务的完成以及提高实验教学质量具有重要作用。本系统分为教师和管理员两个登陆权限， 教师登陆后可以进行选择机房进行排课以及查看自己的排课；管理员登陆后可以审核教师的排课申请、设置机房的可选状态，以及查看机房选择情况如何。";
        aboutView.setContentView(string);
    }
}
