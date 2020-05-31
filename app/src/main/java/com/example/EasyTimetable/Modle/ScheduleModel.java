package com.example.EasyTimetable.Modle;

import android.util.Log;
import com.example.EasyTimetable.Interface.IScheduleBiz;
import com.example.EasyTimetable.Interface.ListenerInterface.QueryClassListener;
import com.example.EasyTimetable.Interface.ListenerInterface.QueryusersclassListner;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ScheduleModel implements IScheduleBiz {
    /*查询某个用户的排课*/
    public void sqlusersclass(final QueryusersclassListner queryusersclassListner) {
        BmobQuery<Schedule> query = new BmobQuery<Schedule>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        User user = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("user", new BmobPointer(user));
        query.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> objects, BmobException e) {
                queryusersclassListner.success(objects);
            }
        });
    }

    /*根据选中得时间和教室查询排课*/
    public void queryclassfromdateandroom(int classroom, BmobDate bmobCreatedAtDate, final QueryClassListener queryClassListener){
        BmobQuery<Schedule> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("bmobDate", bmobCreatedAtDate);
        categoryBmobQuery.addWhereEqualTo("classroom", classroom);
        //categoryBmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);   // 先从缓存获取数据，如果没有，再从网络获取。
        //根据日期查询数据
        categoryBmobQuery.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(final List<Schedule> object, BmobException e) {
                if (e == null) {
                    queryClassListener.success(object);
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    /*管理员审核的查询*/
    public void queryclassforadmin(final QueryClassListener queryClassListener){
        BmobQuery<Schedule> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("zhuangtai", 1);
        categoryBmobQuery.include("user,Schedule.user");
        categoryBmobQuery.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> object, BmobException e) {
                if (e == null) {
                    queryClassListener.success(object);
                } else {
                }
            }
        });
    }
}




