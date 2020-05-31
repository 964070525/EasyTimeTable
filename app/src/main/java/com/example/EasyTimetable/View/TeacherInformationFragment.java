package com.example.EasyTimetable.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.EasyTimetable.Interface.ViewInterface.TeacherInformationView;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Presenter.TeacherInformationPresenter;
import com.example.EasyTimetable.R;
import com.example.EasyTimetable.Modle.User;
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
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class TeacherInformationFragment extends Fragment implements TeacherInformationView {
    private ImageView mHBack;
    private ImageView mHHead;
    private ItemView mNickName;
    private ItemView mXueyuan;
    private ItemView mlook;
    private ItemView mPass;
    private ItemView mAbout;
    private AlertDialog dialog;
    private ItemView mSuggest;
    private ItemView logout;
    private String sugDate;
    private Integer sugtime;
    private int classroom[];
    private Button okbutton;
    private ImageView imgcancle;
    TeacherInformationPresenter teacherInformationPresenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5, container, false);
        mHBack = (ImageView) view.findViewById(R.id.h_back);
        mHHead = (ImageView) view.findViewById(R.id.h_head);
        mNickName = (ItemView) view.findViewById(R.id.Name);
        mXueyuan = (ItemView) view.findViewById(R.id.xueyuan);
        mlook = (ItemView) view.findViewById(R.id.look);
        mPass = (ItemView) view.findViewById(R.id.pass);
        mAbout = (ItemView) view.findViewById(R.id.about);
        mSuggest = (ItemView) view.findViewById(R.id.suggest);
        logout =(ItemView) view.findViewById(R.id.logout);
        classroom = new int[12];
        Glide.with(getActivity()).load(R.drawable.toux)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(mHBack);
        //设置圆形图像
        Glide.with(getActivity()).load(R.drawable.toux)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(mHHead);
        teacherInformationPresenter = new TeacherInformationPresenter(this);
        initData();

        return view;
    }

    void initData() {
        BmobQuery<Schedule> query = new BmobQuery<Schedule>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        final User user = BmobUser.getCurrentUser(User.class);
        mNickName.setRightDesc(user.getNickname());

        mlook.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Intent intent = new Intent(getActivity(), MyTimetableActivity.class);
                getActivity().startActivity(intent);
            }
        });
        mPass.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                initDialog(user);
                dialog.show();
            }
        });
        mAbout.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
               Intent intent = new Intent(getActivity(), AboutActivity.class);
               startActivity(intent);
            }
        });
        mNickName.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
            }
        });
        mXueyuan.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
            }
        });
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
        mSuggest.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                String[] items = new String[7];
                for (int i = 0; i < 7; i++) {
                    items[i] = UtilMethod.getFetureDate(i + 1).split("-")[1] + "月" + UtilMethod.getFetureDate(i + 1).split("-")[2] + "日";
                }
                suggest(items);
            }
        });

    }

    void initDialog(final User user) {
        Button okbutton;
        final Button canclebutton;
        final ImageView imgcancle;
        final EditText oldpsd;
        final EditText newpsd;
        final EditText newpsd1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.addRoom);
        View addRoomView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.psdchangedialog_view, null);
        builder.setView(addRoomView);
        builder.setCancelable(false);
        dialog = builder.create();

        okbutton = (Button) addRoomView.findViewById(R.id.ok_button);
        canclebutton = (Button) addRoomView.findViewById(R.id.cancle_button);
        oldpsd = (EditText) addRoomView.findViewById(R.id.oldpsd);
        imgcancle = (ImageView) addRoomView.findViewById(R.id.cancle_imageview);
        newpsd = (EditText) addRoomView.findViewById(R.id.newpsd);
        newpsd1 = (EditText) addRoomView.findViewById(R.id.newpsd1);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(newpsd.getText().toString().equals(newpsd1.getText().toString()))) {
                    Toast.makeText(getActivity(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    teacherInformationPresenter.changepsd(oldpsd.getText().toString(),newpsd.getText().toString());
                }
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
    @Override
    public void changesuccess(){
        Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
        dialog.cancel();
    }
    @Override
    public void chengefailed(){
        Toast.makeText(getActivity(), "原始密码错误!", Toast.LENGTH_SHORT).show();
        dialog.cancel();
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
                            sugDate = items[choice[0]];
                            String[] items = {"上午", "下午", "晚上"};
                            suggest(items);
                        } else if (items.length == 3) {
                            sugtime = choice[0];
                            initshowsuggest();
                        }
                    }
                });
        builder.create().show();
    }

    void initshowsuggest() {
        BmobQuery<Schedule> categoryBmobQuery = new BmobQuery<>();
        if (sugtime == 0) {
            categoryBmobQuery.addWhereLessThanOrEqualTo("jieci", 5);
        } else if (sugtime == 1) {
            categoryBmobQuery.addWhereLessThanOrEqualTo("jieci", 9);
            categoryBmobQuery.addWhereGreaterThanOrEqualTo("jieci", 6);
        } else {
            categoryBmobQuery.addWhereGreaterThanOrEqualTo("jieci", 10);
        }
        String date = "2020-" + sugDate.substring(0, 2) + "-" + sugDate.substring(3, 5) + " 00:00:00";
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
                    Log.e("chjangud   ", object.size() + "");
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).getZhuangtai() != 1 && object.get(i).getZhuangtai() != 2 &&object.get(i).getZhuangtai()!=-2)
                            classroom[object.get(i).getClassroom() -  401]++;
                    }
                    showsuggestclassroom();
                    Arrays.fill(classroom, 0);
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    void showsuggestclassroom(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.addRoom);
        View addRoomView= LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.suggestdialog_view, null);
        builder.setView(addRoomView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        TextView suggestclassroom = (TextView)addRoomView.findViewById(R.id.suggestclassroom);
        okbutton = (Button)addRoomView.findViewById(R.id.ok_button);
        imgcancle = (ImageView)addRoomView.findViewById(R.id.cancle_imageview);
        StringBuilder str = new StringBuilder();
        int count=0;
        boolean b=false;
        for (int i=0;i<12;i++){
            if(classroom[i]>=2){
                str.append(i+401).append("         ");
                count++;
                b=true;
            }
            if (count%3==0&&count!=0&&b) {
                str.append("\n");
                b=false;
            }
        }
        suggestclassroom.setText(str.toString());

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        imgcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
