package com.example.EasyTimetable.Modle;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Schedule extends BmobObject {
    private Integer jieci;
    private Integer zhuangtai;
    private String taachername;
    private String classname;
    private BmobDate bmobDate;
    private Integer classroom;
    private User user;
    private String reason;


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getClassroom() {
        return classroom;
    }

    public void setClassroom(Integer classroom) {
        this.classroom = classroom;
    }

    public BmobDate getBmobDate() {
        return bmobDate;
    }

    public void setBmobDate(BmobDate bmobDate) {
        this.bmobDate = bmobDate;
    }

    public Integer getJieci() {
        return jieci;
    }

    public Integer getZhuangtai() {
        return zhuangtai;
    }

    public String getTaachername() {
        return taachername;
    }

    public String getClassname() {
        return classname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setJieci(Integer jieci) {
        this.jieci = jieci;
    }

    public void setZhuangtai(Integer zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public void setTaachername(String taachername) {
        this.taachername = taachername;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

}
