package com.example.EasyTimetable.Interface;

import com.example.EasyTimetable.Interface.ListenerInterface.QueryClassListener;
import com.example.EasyTimetable.Interface.ListenerInterface.QueryusersclassListner;

import cn.bmob.v3.datatype.BmobDate;

public interface IScheduleBiz {
    /*查询某个用户的排课*/
     void sqlusersclass(final QueryusersclassListner queryusersclassListner);
    /*根据选中得时间和教室查询排课*/
     void queryclassfromdateandroom(int classroom, BmobDate bmobCreatedAtDate, final QueryClassListener queryClassListener);
    /*管理员审核的查询*/
     void queryclassforadmin(final QueryClassListener queryClassListener);
}




