package com.ljp.hellogithub.activity.viewclicksystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.message_mechanism.MyLinearLayout;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.view.MySlideDemo1View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyViewActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.ll)
    LinearLayout mLl;
    @BindView(R.id.myview)
    MySlideDemo1View mMyview;
    @BindView(R.id.btn3)
    Button mBtn3;
    @BindView(R.id.ll_main)
    MyLinearLayout mLlMain;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showToast("可以点击了");
                    mLlMain.setInterceptClick(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        ButterKnife.bind(this);

        mTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTv.setText("x:" + mLl.getX() + "-y:" + mLl.getY() +
                        "\nmTv-x:" + mTv.getX());
                mTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        //        String str = String.valueOf(ViewConfiguration.get(this).getScaledTouchSlop());
        //        showToast(str);
        //
        //        ObjectAnimator.ofFloat(mBtn, "translationX", 0, 100).setDuration(100)
        //                .start();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(Demo1Activity.class);
                break;
            case R.id.btn2:
                startActivity(Demo2Activity.class);
                break;
            case R.id.btn3:
                showToast("禁止点击事件10秒");
                mLlMain.setInterceptClick(true);
                mHandler.sendEmptyMessageDelayed(1,1000*10);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
