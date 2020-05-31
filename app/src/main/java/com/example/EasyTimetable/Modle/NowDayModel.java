package com.example.EasyTimetable.Modle;



import android.util.Log;

import com.example.EasyTimetable.Interface.INowDayBiz;
import com.example.EasyTimetable.Interface.ListenerInterface.QueryNowDayListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class NowDayModel implements INowDayBiz {
    @Override
    public void queryNowday(final QueryNowDayListener queryNowDayListener){
        BmobQuery<NowDay> bmobQuery = new BmobQuery<NowDay>();
        bmobQuery.getObject("P0iQKKKO", new QueryListener<NowDay>() {
            @Override
            public void done(NowDay object, BmobException e) {
                if(e==null){
                    queryNowDayListener.success(object);
                }else{
                    Log.e("error",e.toString());
                }
            }
        });
    }

}
