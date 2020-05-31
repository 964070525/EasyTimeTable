package com.example.EasyTimetable.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.EasyTimetable.Interface.ViewInterface.CourseInformationView;
import com.example.EasyTimetable.Modle.User;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Presenter.CourseInformationPresenter;
import com.example.EasyTimetable.R;
import com.example.EasyTimetable.Util.UtilMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AdminShowClassFragment extends Fragment implements CourseInformationView {
    private ListView listView;
    private List<Schedule> list;
    private Adapter adapter;
    private CourseInformationPresenter courseInformationPresenter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        listView = view.findViewById(R.id.listview);
        /*
         * 从SharedPreferences中获取当前应该显示哪个日期的信息
         * */
        SharedPreferences preferences = getActivity().getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
        int day = preferences.getInt("day1", 555);
        int classroom = preferences.getInt("classroom", 401);
        courseInformationPresenter = new CourseInformationPresenter(this);
        /*
         *显示信息
         * */
        getData(day,classroom);
        return view;
    }

    /*
     *根据日期查询一天的数据然后放到listview中去
     * */
    public void getData(int day,int classroom) {
        String createdAt = UtilMethod.getFetureDate(day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        courseInformationPresenter.initlistview(classroom,bmobCreatedAtDate);
    }

    @Override
    public void initlistview(List<Schedule> object) {
        adapter = new Adapter(getActivity(), R.layout.item, object);
        listView.setAdapter(adapter);
    }

    class Adapter extends ArrayAdapter<Schedule> {
        private int newResourceId;
        public Adapter(Context context, int resourceId, List<Schedule> Paikelist) {
            super(context, resourceId, Paikelist);
            newResourceId = resourceId;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Schedule p = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(newResourceId, parent, false);
            TextView JC = view.findViewById(R.id.JC);
            TextView CR = view.findViewById(R.id.CR);
            final TextView ZT = view.findViewById(R.id.ZT);
            JC.setText("第"+p.getJieci() + "节课               ");
            CR.setText(p.getClassroom()+"");
            if (p.getZhuangtai()==1||p.getZhuangtai()==2) {
                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject(p.getUser().getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User object,BmobException e) {
                        if(e==null){
                            ZT.setText(object.getNickname()+"-"+p.getClassname());
                        }else{
                        }
                    }
                });
            }
            else if(p.getZhuangtai()==-2)
                ZT.setText("不可选");
            else
                ZT.setText("空闲");
            return view;
        }
    }

}
