package com.ljp.hellogithub.activity.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ServiceDemoActivity extends BaseActivity {

    HelloService mServiceDemo;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start://开始服务
                startService(new Intent(this,HelloService.class));
                break;
            case R.id.btn_stop://停止服务
                stopService(new Intent(this,HelloService.class));
                break;
        }
    }
}
