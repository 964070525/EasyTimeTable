package com.example.EasyTimetable.Interface.ListenerInterface;

import com.example.EasyTimetable.Modle.NowDay;
import com.example.EasyTimetable.Modle.Schedule;

import java.util.List;

public interface QueryNowDayListener {
    //成功的回调
    void success(NowDay nowDay);
    //失败的回调
    void failed();
}
