package com.example.EasyTimetable.View;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.EasyTimetable.Interface.ViewInterface.MyTimetableView;
import com.example.EasyTimetable.Modle.Schedule;
import com.example.EasyTimetable.Presenter.MyTimetablePresenter;
import com.example.EasyTimetable.R;

import java.util.ArrayList;
import java.util.List;

public class MyTimetableActivity extends AppCompatActivity implements MyTimetableView {
    private ListView mylistview1;
    private ListView mylistview2;
    private ListView mylistview3;
    private Button button;
    private List<Schedule> list;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private ItemView i1;
    private ItemView i2;
    private ItemView i3;
    private List<Schedule> lists3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_timetable);
        button = (Button) findViewById(R.id.back);
        mylistview1 = (ListView) findViewById(R.id.listview_teacher1);
        mylistview2 = (ListView) findViewById(R.id.listview_teacher2);
        mylistview3 = (ListView) findViewById(R.id.listview_teacher3);
        i1 = (ItemView) findViewById(R.id.i1);
        i2 = (ItemView) findViewById(R.id.i2);
        i3 = (ItemView) findViewById(R.id.i3);
        i1.setShowRightArrow(false);
        i2.setShowRightArrow(false);
        i3.setShowRightArrow(false);
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        MyTimetablePresenter myTimetablePresenter = new MyTimetablePresenter(this);
        myTimetablePresenter.initlistview();

        i1.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {

            }
        });
        i2.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {

            }
        });
        i3.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MyTimetableActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
        mylistview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initDialog(position);
            }
        });

    }

  @Override
    public void initlistview(List<Schedule>lists1,List<Schedule>lists2,List<Schedule>lists3){
        this.lists3=lists3;
        Adapter adapter1 = new Adapter(getApplicationContext(), R.layout.item_teacher, lists1, 1);
        Adapter adapter2 = new Adapter(getApplicationContext(), R.layout.item_teacher, lists2, 2);
        Adapter adapter3 = new Adapter(getApplicationContext(), R.layout.item_teacher, lists3, -1);
        mylistview1.setAdapter(adapter1);
        mylistview2.setAdapter(adapter2);
        mylistview3.setAdapter(adapter3);
        setListViewHeightBasedOnChildren(mylistview1);
        setListViewHeightBasedOnChildren(mylistview2);
        setListViewHeightBasedOnChildren(mylistview3);
    }

    class Adapter extends ArrayAdapter<Schedule> {
        private int newResourceId;
        int cccc;

        public Adapter(Context context, int resourceId, List<Schedule> Paikelist, int i) {
            super(context, resourceId, Paikelist);
            newResourceId = resourceId;
            cccc = i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Schedule p = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(newResourceId, parent, false);
            if (p.getZhuangtai() == cccc) {
                TextView SJ = view.findViewById(R.id.SJ_teacher);
                TextView JS = view.findViewById(R.id.JS_teacher);
                TextView KC = view.findViewById(R.id.JS_classname);
                final TextView ZT = view.findViewById(R.id.ZT_teacher);
                SJ.setText(p.getBmobDate().getDate().substring(0, 10) + "    第" + p.getJieci() + "节课");
                JS.setText(p.getClassroom() + "");
                KC.setText(p.getClassname() + "");
                if (p.getZhuangtai() == 1) {
                    ZT.setText("待审核");
                } else if (p.getZhuangtai() == 2)
                    ZT.setText("审核通过");
                else if (p.getZhuangtai() == -1)
                    ZT.setText("审核被打回");
            }
            return view;
        }
    }

    void initDialog(int index) {
        final Schedule p = lists3.get(index);
        if (p.getZhuangtai() == -1) {
            builder = new AlertDialog.Builder(this, R.style.addRoom);
            View addRoomView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.lookreasondialog_view, null);
            builder.setView(addRoomView);
            builder.setCancelable(false);
            dialog = builder.create();
            TextView reason = (TextView) addRoomView.findViewById(R.id.reason);
            Button okbutton = (Button) addRoomView.findViewById(R.id.ok_button);
            ImageView imgcancle = (ImageView) addRoomView.findViewById(R.id.cancle_imageview);
            reason.setText(p.getReason() + "");
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        final Adapter listAdapter = (Adapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
