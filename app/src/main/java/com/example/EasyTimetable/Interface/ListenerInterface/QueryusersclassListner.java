package com.example.EasyTimetable.Interface.ListenerInterface;

import com.example.EasyTimetable.Modle.Schedule;

import java.util.List;

public interface QueryusersclassListner {
    //成功的回调
    void success(List<Schedule> list);
    //失败的回调
    void failed();
}
