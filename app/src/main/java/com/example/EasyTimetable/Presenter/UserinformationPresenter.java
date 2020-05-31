package com.example.EasyTimetable.Presenter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import com.example.EasyTimetable.Interface.IUserBiz;
import com.example.EasyTimetable.Interface.ViewInterface.IUserView;
import com.example.EasyTimetable.Modle.User;
import com.example.EasyTimetable.Modle.UserModel;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class UserinformationPresenter {
    private IUserBiz userBiz;
    private IUserView iUserView;
    private Handler mHandler = new Handler();
    //对应视图页面销毁的标志位,当视图销毁后回调就不需要处理了
    private boolean destroyFlag;
    private User user;
    private Bitmap bitmap;
    /*
    * Presenter 的初始化
    * */
    public UserinformationPresenter(IUserView iUserView) {
        this.iUserView = iUserView;
        this.userBiz = new UserModel();
        user = BmobUser.getCurrentUser(User.class);
    }

    public void init(){
        user.getAvatar().download(new DownloadFileListener() {
            @Override
                    public void onProgress(Integer integer, long l) {
                    }
                    @Override
                    public void done(String s, BmobException e) {
                        if(e == null){
                            //设置圆形头像并显示
                          //  bitmap = round_Util.toRoundBitmap(BitmapFactory.decodeFile(s));
                            //iUserView.initview(user.getNickname(),bitmap);
                }
                else{
                    Log.e("asd",e.toString());
                }
            }
        });
    }
}
