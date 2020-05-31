package com.example.EasyTimetable.Presenter;

import android.util.Log;

import com.example.EasyTimetable.Interface.ListenerInterface.QueryNowDayListener;
import com.example.EasyTimetable.Modle.NowDay;
import com.example.EasyTimetable.Modle.NowDayModel;
import com.example.EasyTimetable.Modle.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.EasyTimetable.Util.UtilMethod.getFetureDate;

public class AdminPresenter {
    private NowDayModel nowDayModel;
    public AdminPresenter(){
        nowDayModel = new NowDayModel();
    }
    public void init() {
        Log.e("wqe","jinruleinit");
        nowDayModel.queryNowday(new QueryNowDayListener() {
            @Override
            public void success(NowDay nowDay) {
                Log.e("weq", nowDay.getNowday()+"wqeeeeee"+getFetureDate(0));
                if (!(nowDay.getNowday().equals(getFetureDate(0)))) {
                    Log.e("weq", "不是今天");
                    cunchu();
                    nowDay.setNowday(getFetureDate(0));
                    nowDay.update(nowDay.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                            } else {
                            }
                        }
                    });
                }else
                    Log.e("weq", "是今天");
            }
            @Override
            public void failed() {
                Log.e("weq", "cucuo");
            }
        });
    }
    void cunchu() {
        final List<BmobObject> paikes1 = new ArrayList<>();
        final List<BmobObject> paikes2 = new ArrayList<>();
        final List<BmobObject> paikes3 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            String createdAt = getFetureDate(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date createdAtDate = null;
            try {
                createdAtDate = sdf.parse(createdAt);
            } catch (ParseException e) {
            }
            BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
            for (int classroom = 401; classroom <= 405; classroom++) {
                for (int j = 1; j <= 12; j++) {
                    Schedule p = new Schedule();
                    p.setClassroom(classroom);
                    p.setBmobDate(bmobCreatedAtDate);
                    p.setJieci(j);
                    p.setZhuangtai(0);
                    paikes1.add(p);
                }
            }
            for (int classroom = 406; classroom <= 410; classroom++) {
                for (int j = 1; j <= 12; j++) {
                    Schedule p = new Schedule();
                    p.setClassroom(classroom);
                    p.setBmobDate(bmobCreatedAtDate);
                    p.setJieci(j);
                    p.setZhuangtai(0);
                    paikes2.add(p);
                }
            }
            for (int classroom = 411; classroom <= 412; classroom++) {
                for (int j = 1; j <= 12; j++) {
                    Schedule p = new Schedule();
                    p.setClassroom(classroom);
                    p.setBmobDate(bmobCreatedAtDate);
                    p.setJieci(j);
                    p.setZhuangtai(0);
                    paikes3.add(p);
                }
            }
        }
        cuncushuju(paikes1);
        cuncushuju(paikes2);
        cuncushuju(paikes3);
    }
    void cuncushuju(List<BmobObject> paikes) {
        new BmobBatch().insertBatch(paikes).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                        } else {
                        }
                    }
                } else {
                    Log.e("qwew", e.getMessage() + "," + e.getErrorCode());
                    //Snackbar.make(textView, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
