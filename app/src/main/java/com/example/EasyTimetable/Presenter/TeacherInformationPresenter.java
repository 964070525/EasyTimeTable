package com.example.EasyTimetable.Presenter;

import com.example.EasyTimetable.Interface.ListenerInterface.UserPsdChangeListener;
import com.example.EasyTimetable.Interface.ViewInterface.TeacherInformationView;
import com.example.EasyTimetable.Modle.UserModel;

public class TeacherInformationPresenter {
    private UserModel userModel;
    private TeacherInformationView teacherInformationView;
    public  TeacherInformationPresenter(TeacherInformationView teacherInformationView){
        this.teacherInformationView = teacherInformationView;
        userModel = new UserModel();
    }

    public void changepsd(String oldpsd,String newpsd){
        userModel.changePsd(oldpsd, newpsd, new UserPsdChangeListener() {
            @Override
            public void success() {
                teacherInformationView.changesuccess();
            }
            @Override
            public void failed() {
                teacherInformationView.chengefailed();
            }
        });
    }
}
