package com.example.EasyTimetable.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.EasyTimetable.Interface.ViewInterface.CourseInformationView;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Presenter.CourseInformationPresenter;
import com.example.EasyTimetable.R;
import com.example.EasyTimetable.Modle.User;
import com.example.EasyTimetable.Util.UtilMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


public class CourseInformationFragment extends Fragment implements CourseInformationView {
    private ListView listView;
    private Button okbutton;
    private Button canclebutton;
    private ImageView imgcancle;
    private EditText editText;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private List<Schedule> list;
    private Adapter adapter;
    private CourseInformationPresenter courseInformationPresenter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);
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
    public void initlistview(List<Schedule> object){
        list=object;
        adapter = new Adapter(getActivity(), R.layout.item, object);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initDialog(position);
                dialog.show();
            }
        });
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
                ZT.setText("已排课");
            }
            else if (p.getZhuangtai()==-2)
                ZT.setText("不可选");
            else
                ZT.setText("可选");
            return view;
        }
    }

    void initDialog(int index){
        builder=new AlertDialog.Builder(getActivity(),R.style.addRoom);
        View addRoomView= LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.customdialog_view, null);
        builder.setView(addRoomView);
        builder.setCancelable(false);
        dialog = builder.create();

        TextView time = (TextView)addRoomView.findViewById(R.id.time);
        TextView classroom = (TextView)addRoomView.findViewById(R.id.classroom);
        okbutton = (Button)addRoomView.findViewById(R.id.ok_button);
        canclebutton = (Button)addRoomView.findViewById(R.id.cancle_button);
        editText = (EditText)addRoomView.findViewById(R.id.room_name_dt);
        imgcancle = (ImageView)addRoomView.findViewById(R.id.cancle_imageview);
        final Schedule p = list.get(index);
        time.setText(p.getBmobDate().getDate().substring(0,10)+"   第"+p.getJieci()+"节课");
        classroom.setText(p.getClassroom()+"");
        if(p.getZhuangtai()==1||p.getZhuangtai()==2||p.getZhuangtai()==-2){
            if(p.getZhuangtai()==-2)
                editText.setText("该时间段不可选");
            else
                editText.setText("该时间段已被选");

            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            okbutton.setEnabled(false);

        }
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qew",editText.getText().toString());
                final BmobQuery<User> bmobQuery = new BmobQuery<User>();
                User user = BmobUser.getCurrentUser(User.class);
                bmobQuery.getObject(user.getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User object,BmobException e) {
                        if(e==null){
                            User user ;
                            user = object;
                            p.setUser(user);
                            p.setZhuangtai(1);
                            p.setClassname(editText.getText().toString());
                            p.update(p.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        adapter.notifyDataSetChanged();
                                    }else{
                                    }
                                }
                            });
                        }else{
                        }
                    }
                });
                dialog.cancel();
                Toast.makeText(getActivity(),"申请成功!",Toast.LENGTH_SHORT).show();
            }
        });
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // builder.create().dismiss();
                dialog.cancel();
            }
        });
        imgcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

}
