package com.ljp.hellogithub.activity.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/5/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ServiceDemoActivity extends BaseActivity implements ServiceConnection {

    HelloService mServiceDemo;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;
    @BindView(R.id.btn_bind)
    Button mBtnBind;
    @BindView(R.id.btn_unbind)
    Button mBtnUnbind;
    @BindView(R.id.btn_start_musice)
    Button mBtnStartMusice;
    @BindView(R.id.btn_stop_musice)
    Button mBtnStopMusice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind,R.id.btn_start_musice, R.id.btn_stop_musice})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_start://开始服务
                startService(new Intent(this, HelloService.class));
                break;
            case R.id.btn_stop://停止服务
                stopService(new Intent(this, HelloService.class));
                break;
            case R.id.btn_bind:
                intent = new Intent(this, HelloService.class);
                bindService(intent, this, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(this);
                break;
            case R.id.btn_start_musice:
                break;
            case R.id.btn_stop_musice:
                break;

        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //只有当我们自己写的MyService的onBind方法返回值不为null时，才会被调用
        Log.e("call", "onServiceConnected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        //这个方法只有出现异常时才会调用，服务器正常退出不会调用。
        Log.e("call", "onServiceDisconnected");
    }

}
