package com.example.EasyTimetable.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.EasyTimetable.Interface.ViewInterface.CourseInformationView;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Presenter.CourseInformationPresenter;
import com.example.EasyTimetable.R;
import com.example.EasyTimetable.Util.UtilMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class AdministratorFragment extends Fragment implements CourseInformationView {
    private ImageView mHBack;
    private ImageView mHHead;
    private ListView mylistview;
    private Button okbutton;
    private Button canclebutton;
    private ImageView imgcancle;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private List<Schedule> list;
    private Adapter adapter;
    private ItemView logout;
    private ItemView setnotchoose;
    private String setDate;
    private Integer settime;
    private CourseInformationPresenter courseInformationPresenter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        mHBack = (ImageView) view.findViewById(R.id.h_back);
        mHHead = (ImageView) view.findViewById(R.id.h_head);
        mylistview = (ListView) view.findViewById(R.id.listview_teacher);
        logout = (ItemView)view.findViewById(R.id.logout);
        setnotchoose = (ItemView)view.findViewById(R.id.setnotchoose);
        Glide.with(getActivity()).load(R.drawable.ypk)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(mHBack);
        //设置圆形图像
        Glide.with(getActivity()).load(R.drawable.ypk)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(mHHead);
        courseInformationPresenter = new CourseInformationPresenter(this);
        initData();
        logout.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ypk1).setTitle("是否退出登录")
                        .setMessage("确定要退出登陆吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                BmobUser.logOut();
                                getActivity().finish();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                getActivity().startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
        setnotchoose.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                String[] items = new String[7];
                for (int i = 0; i < 7; i++) {
                    items[i] = UtilMethod.getFetureDate(i + 1).split("-")[1] + "月" + UtilMethod.getFetureDate(i + 1).split("-")[2] + "日";
                }
                suggest(items);
            }
        });

        return view;
    }

    void initData() {
        courseInformationPresenter.initlistview();
    }

    @Override
    public void initlistview(List<Schedule> object) {
        list = object;
        adapter = new Adapter(getActivity(), R.layout.item_adminshenghe, object);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            TextView SJ = view.findViewById(R.id.SJ_admin);
            TextView JS = view.findViewById(R.id.JS_admin);
            final TextView KC = view.findViewById(R.id.KC_admin);
            SJ.setText(p.getBmobDate().getDate().substring(0, 10) + "      第" + p.getJieci() + "节");
            JS.setText(p.getClassroom() + "");
            KC.setText(p.getUser().getNickname() + "—" + p.getClassname());
            return view;
        }
    }

    void initDialog(int index) {

        builder = new AlertDialog.Builder(getActivity(), R.style.addRoom);
        View addRoomView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.adminshenghedialog_view, null);
        builder.setView(addRoomView);
        builder.setCancelable(false);
        dialog = builder.create();
        TextView time = (TextView) addRoomView.findViewById(R.id.time);
        TextView classroom = (TextView) addRoomView.findViewById(R.id.classroom);
        TextView classname = (TextView) addRoomView.findViewById(R.id.classname);
        okbutton = (Button) addRoomView.findViewById(R.id.ok_button);
        canclebutton = (Button) addRoomView.findViewById(R.id.cancle_button);
        imgcancle = (ImageView) addRoomView.findViewById(R.id.cancle_imageview);
        final Schedule p = list.get(index);
        time.setText(p.getBmobDate().getDate().substring(0, 10) + "   第" + p.getJieci() + "节课");
        classroom.setText(p.getClassroom() + "");
        classname.setText(p.getUser().getNickname() + "—" + p.getClassname());
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.setZhuangtai(2);
                p.update(p.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                            initData();
                            Toast.makeText(getActivity(), "审核成功!", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    }
                });
            }
        });
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(p);
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

    void initDialog(final Schedule p) {
        Button okbutton;
        final Button canclebutton;
        final EditText result;
        final ImageView imgcancle;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.addRoom);
        View addRoomView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.backresultdialog_view, null);
        builder.setView(addRoomView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        okbutton = (Button) addRoomView.findViewById(R.id.ok_button);
        imgcancle = (ImageView) addRoomView.findViewById(R.id.cancle_imageview);
        result = (EditText) addRoomView.findViewById(R.id.result);
        dialog.show();
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.setReason(result.getText().toString());
                p.setZhuangtai(-1);
                p.update(p.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                            initData();
                            Toast.makeText(getActivity(), "审核成功!", Toast.LENGTH_SHORT).show();
                        }else{
                        }
                    }
                });
            }
        });
        imgcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    void suggest(final String[] items) {
        AlertDialog.Builder builder;
        final int[] choice = {0};
        builder = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ypk1).setTitle("请选择想安排课程的时间")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice[0] = i;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (items.length == 7) {
                            setDate = items[choice[0]];
                            String[] items = {"上午", "下午", "晚上"};
                            suggest(items);
                        } else if (items.length == 3) {
                            settime = choice[0];
                            initshowsuggest();
                            Toast.makeText(getActivity(),"设置成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.create().show();
    }

    void initshowsuggest() {
        BmobQuery<Schedule> categoryBmobQuery = new BmobQuery<>();
        if (settime == 0) {
            categoryBmobQuery.addWhereLessThanOrEqualTo("jieci", 5);
        } else if (settime == 1) {
            categoryBmobQuery.addWhereLessThanOrEqualTo("jieci", 9);
            categoryBmobQuery.addWhereGreaterThanOrEqualTo("jieci", 6);
        } else {
            categoryBmobQuery.addWhereGreaterThanOrEqualTo("jieci", 10);
        }
        String date = "2020-" + setDate.substring(0, 2) + "-" + setDate.substring(3, 5) + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        categoryBmobQuery.addWhereEqualTo("bmobDate", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> object, BmobException e) {
                if (e == null) {
                    for (Schedule schedule:object){
                        schedule.setZhuangtai(-2);
                        schedule.update(schedule.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                }else{
                                }
                            }
                        });
                    }
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }


}
