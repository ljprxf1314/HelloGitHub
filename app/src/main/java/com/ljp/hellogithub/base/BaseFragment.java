package com.ljp.hellogithub.base;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ljp.hellogithub.view.CustomProgress;

/**
 * Created by lijipei on 2016/8/22.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 键盘管理对象
     */
    protected InputMethodManager manager;

    /**
     * 状态栏通知的管理类
     */
    protected NotificationManager notificationManager;
    String tag = getClass().getSimpleName();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        OnActCreate(savedInstanceState);
        notificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 子类实现的onActivityCreated方法
     * @param savedInstanceState
     */
    public abstract void OnActCreate(Bundle savedInstanceState);


    /**
     * 设置状态
     */
    public void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

        /**
     * 跳转一个界面不传递数据
     *
     * @param clazz
     */
    public void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * toast
     * @param info
     */
    public void showToast(String info) {
        Toast toast= Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 自己填写信息的ProgressDialog
     */
    public void showProgressDialog(Context context, String message) {
        if (message == null || message.equals("")) {
            showProgressDialog(context);
        } else {
            CustomProgress.show(getActivity(), message, true, null);
        }
    }

    public void showProgressDialog(Context context) {
        CustomProgress.show(context, "加载中...", true, null);
    }


    public int screenWidth;
    public int screenHeigh;
    // 屏幕密度dpi
    public int densityDpi;

    /**
     * 获取屏幕信息
     */
    public void getScreenDetails() {
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
        densityDpi = dm.densityDpi;
    }


    /** 销毁界面--由于网络回调有时候销毁不掉界面而写的方法 */
    protected void finishActivity(){
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
