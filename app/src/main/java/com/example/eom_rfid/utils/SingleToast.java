package com.example.eom_rfid.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eom_rfid.R;
import com.example.eom_rfid.base.BaseApplication;


public class SingleToast {

    private static Toast toast = null;
    private static SingleToast singleToast;

    private SingleToast() {
    }


    public static SingleToast getInstance() {
        if (null == singleToast) {
//            synchronized (SingleToast.class) {
//                if (null == singleToast)
//                    singleToast = new SingleToast();
//            }
            singleToast = new SingleToast();
        }
        return singleToast;
    }

    private Context context = null;
    ImageView imageView;
    TextView textView;
    int state, SUCCEED = 0, FAILED = 1, LOADING = 10000;
    View view;


    public void toast(int state, String message) {
        if (context == null) {
            context = BaseApplication.getInstance().getApplicationContext();
            init();
        }
        if (state == LOADING) {
            imageView.setImageResource(R.mipmap.toast_tip_loading);
            startRotateAnimation(imageView);
        } else if (state == SUCCEED) {
            imageView.setImageResource(R.mipmap.toast_tip_success);
        } else if (state == FAILED) {
            imageView.setImageResource(R.mipmap.toast_tip_fail);
        }
        textView.setText(message);
        toast.setView(view);
        toast.show();
    }

    public static void startRotateAnimation(View v) {
        RotateAnimation mRotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 创建旋转动画对象，从0°旋转到360°，以自身中心点为旋转轴
        mRotateAnimation.setDuration(1000);
        // 从0°旋转到360°所需要的时间
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        // 线性插入器（匀速旋转）
        mRotateAnimation.setRepeatCount(-1);
        // 重复次数，-1无限循环
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        // 重复模式：restart重新开始
        v.startAnimation(mRotateAnimation);
        // 开始动画
    }


    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.toast_tip, null);
        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        toast = new Toast(context);
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
    }
}
