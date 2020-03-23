package com.ljp.hellogithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.activity.animation.AnimationMainActivity;
import com.ljp.hellogithub.activity.annotation.AnnotationMainActivity;
import com.ljp.hellogithub.activity.io.FileIOMainActivity;
import com.ljp.hellogithub.activity.message_mechanism.MessageMainActivity;
import com.ljp.hellogithub.activity.message_mechanism.MessageMechanismActivity;
import com.ljp.hellogithub.activity.mvp.MvpActivity;
import com.ljp.hellogithub.activity.network.NetworkMainActivity;
import com.ljp.hellogithub.activity.rxjava.RxJavaActivity;
import com.ljp.hellogithub.activity.service.ServiceDemoActivity;
import com.ljp.hellogithub.activity.textview.TextViewSpannableActivity;
import com.ljp.hellogithub.activity.threadpool.MyThreadMain;
import com.ljp.hellogithub.activity.viewclicksystem.MyViewActivity;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.ui.UIActivity;
import com.ljp.hellogithub.util.LaunchTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_activity)
    Button mBtnActivity;
    @BindView(R.id.btn_my_view)
    Button mBtnMyView;
    @BindView(R.id.btn_threadpool)
    Button mBtnThreadpool;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LaunchTimer.endRecord("onWindowFocusChanged-");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LaunchTimer.endRecord("onCreate-");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LaunchTimer.endRecord("onResume-");
    }

    @OnClick({R.id.btn_activity, R.id.btn_my_view, R.id.btn_service, R.id.btn_animation, R.id.btn_ui,
            R.id.btn_message, R.id.btn_threadpool, R.id.btn_fileio,
            R.id.btn_rxjava, R.id.btn_annotation, R.id.btn_network,R.id.btn_mvp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_activity:
                startActivity(TextViewSpannableActivity.class);
                break;
            case R.id.btn_my_view:
                startActivity(MyViewActivity.class);
                break;
            case R.id.btn_service:
                startActivity(ServiceDemoActivity.class);
                break;
            case R.id.btn_animation:
                startActivity(AnimationMainActivity.class);
                break;
            case R.id.btn_ui:
                startActivity(UIActivity.class);
                break;
            case R.id.btn_message://android消息机制
                startActivity(MessageMainActivity.class);
                break;
            case R.id.btn_threadpool:
                startActivity(MyThreadMain.class);
                break;
            case R.id.btn_rxjava:
                startActivity(RxJavaActivity.class);
                break;
            case R.id.btn_fileio:
                startActivity(FileIOMainActivity.class);
                break;
            case R.id.btn_annotation://注解与反射
                Intent intent = new Intent(this, AnnotationMainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_network:
                startActivity(NetworkMainActivity.class);
                break;
            case R.id.btn_mvp:
                startActivity(MvpActivity.class);
                break;
        }
    }
}
