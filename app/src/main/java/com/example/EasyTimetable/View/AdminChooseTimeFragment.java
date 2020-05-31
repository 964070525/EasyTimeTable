package com.example.EasyTimetable.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.EasyTimetable.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import static com.example.EasyTimetable.Util.UtilMethod.getFetureDate;

public class AdminChooseTimeFragment extends Fragment {
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        init(view);
        refrsh();
        return view;
    }

    void init(View view){
        final SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putInt("day1",1);
        editor.apply();
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationview);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        /*初始化TabLayout*/
        mTabLayout=view.findViewById(R.id.mTabLayout);
        for (int i=1;i<8;i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(getFetureDate(i).split("-")[1]+"月"+getFetureDate(i).split("-")[2]+"日"));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals(getFetureDate(1).split("-")[1]+"月"+getFetureDate(1).split("-")[2]+"日")){
                    editor.putInt("day1",1);
                    editor.apply();
                    refrsh();
                }
                if (tab.getText().equals(getFetureDate(2).split("-")[1]+"月"+getFetureDate(2).split("-")[2]+"日")){
                    editor.putInt("day1",2);
                    editor.apply();
                    refrsh();
                }
                if (tab.getText().equals(getFetureDate(3).split("-")[1]+"月"+getFetureDate(3).split("-")[2]+"日")){
                    editor.putInt("day1",3);
                    editor.apply();
                    refrsh();
                }
                if (tab.getText().equals(getFetureDate(4).split("-")[1]+"月"+getFetureDate(4).split("-")[2]+"日")){
                    editor.putInt("day1",4);
                    editor.apply();
                    refrsh();
                }
                if (tab.getText().equals(getFetureDate(5).split("-")[1]+"月"+getFetureDate(5).split("-")[2]+"日")){
                    editor.putInt("day1",5);
                    editor.apply();
                    refrsh();
                }
                if (tab.getText().equals(getFetureDate(6).split("-")[1]+"月"+getFetureDate(6).split("-")[2]+"日")){
                    editor.putInt("day1",6);
                    editor.apply();
                    refrsh();
                }
                if (tab.getText().equals(getFetureDate(7).split("-")[1]+"月"+getFetureDate(7).split("-")[2]+"日")){
                    editor.putInt("day1",7);
                    editor.apply();
                    refrsh();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mToggle.syncState();
        mDrawerLayout.setDrawerListener(mToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.one:
                        editor.putInt("classroom",401);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.two:
                        editor.putInt("classroom",402);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.three:
                        editor.putInt("classroom",403);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.four:
                        editor.putInt("classroom",404);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.five:
                        editor.putInt("classroom",405);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.six:
                        editor.putInt("classroom",406);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.seven:
                        editor.putInt("classroom",407);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.eight:
                        editor.putInt("classroom",408);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nine:
                        editor.putInt("classroom",409);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.ten:
                        editor.putInt("classroom",410);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.eleven:
                        editor.putInt("classroom",411);
                        editor.apply();
                        refrsh();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.twelve:
                        editor.putInt("classroom",412);
                        editor.apply();
                        mDrawerLayout.closeDrawers();
                        refrsh();
                        break;
                }
                return true;
            }
        });
    }

    void refrsh(){
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AdminShowClassFragment frag1 = new AdminShowClassFragment();
        transaction.replace(R.id.fl_container1, frag1);
        transaction.commit();
    }
}
