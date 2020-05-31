package com.example.EasyTimetable.Presenter;

import com.example.EasyTimetable.Interface.ListenerInterface.QueryusersclassListner;
import com.example.EasyTimetable.Interface.ViewInterface.MyTimetableView;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Modle.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class MyTimetablePresenter {
    private MyTimetableView myTimetableView;
    private ScheduleModel scheduleModel;
    private List<Schedule> lists3 = new ArrayList<>();
    private List<Schedule> lists2 = new ArrayList<>();
    private List<Schedule> lists1 = new ArrayList<>();
    public MyTimetablePresenter(MyTimetableView myTimetableView) {
        this.myTimetableView = myTimetableView;
        scheduleModel = new ScheduleModel();
    }


    public void initlistview() {
        scheduleModel.sqlusersclass(new QueryusersclassListner() {
            @Override
            public void success(List<Schedule> list) {
                for (Schedule p : list) {
                    if (p.getZhuangtai() == 1) {
                        lists1.add(p);
                    } else if (p.getZhuangtai() == 2) {
                        lists2.add(p);
                    } else
                        lists3.add(p);
                }
                myTimetableView.initlistview(lists1,lists2,lists3);
            }
            @Override
            public void failed() {

            }
        });

    }
}
