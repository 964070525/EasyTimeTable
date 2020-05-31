package com.example.EasyTimetable.Presenter;

import com.example.EasyTimetable.Interface.ListenerInterface.QueryClassListener;
import com.example.EasyTimetable.Interface.ViewInterface.CourseInformationView;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Modle.ScheduleModel;

import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class CourseInformationPresenter {
    private CourseInformationView courseInformationView;
    private ScheduleModel scheduleModel;

    public CourseInformationPresenter(CourseInformationView courseInformationView){
        this.courseInformationView = courseInformationView;
        scheduleModel = new ScheduleModel();
    }

    public void initlistview(int classroom, BmobDate bmobCreatedAtDate){
        scheduleModel.queryclassfromdateandroom(classroom, bmobCreatedAtDate, new QueryClassListener() {
            @Override
            public void success(List<Schedule> list) {
                courseInformationView.initlistview(list);
            }
            @Override
            public void failed() {
            }
        });
    }
    public void initlistview(){
        scheduleModel.queryclassforadmin(new QueryClassListener() {
            @Override
            public void success(List<Schedule> list) {
                courseInformationView.initlistview(list);
            }
            @Override
            public void failed() {

            }
        });
    }
}






