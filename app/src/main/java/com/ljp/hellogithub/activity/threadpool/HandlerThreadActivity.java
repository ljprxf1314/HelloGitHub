package com.ljp.hellogithub.activity.threadpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerThreadActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.btn)
    Button mBtn;


    private HandlerThread mHandlerThread;

    //子线程中的handler
    private Handler handler;
    //UI线程中的handler
    private Handler mMainHandler = new Handler();
    //以防退出界面后Handler还在执行
    private boolean isUpdateInfo;
    //用以表示该handler的常数
    private static final int MSG_UPDATE_INFO = 0x110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        ButterKnife.bind(this);

        //创建handlerThread
        mHandlerThread = new HandlerThread("thread-handler");
        /*//开启一个线程
        mHandlerThread.start();
        //在mHandlerThread线程中创建一个handler对象
        final Handler handler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //这个方法是运行在 handler-thread 线程中的 ，可以执行耗时操作
                Log.e("handler ", "消息： " + msg.what + "  线程： " + Thread.currentThread().getName());
            }
        };

        //在主线程给handler发送消息
        handler.sendEmptyMessage(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程给handler发消息
                handler.sendEmptyMessage(2);
            }
        }).start();*/


        //---------------------模拟时间刷新-------------------------
        mHandlerThread.start();
        handler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                update();
                if (isUpdateInfo)
                    this.sendEmptyMessage(MSG_UPDATE_INFO);
            }
        };
    }

    private void update() {
        try {
            Thread.sleep(2000);
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    String result = "每隔2秒更新一下数据：";
                    result += Math.random();
                    mTv.setText(result);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        handler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //停止查询
        //以防退出界面后Handler还在执行
        isUpdateInfo = false;
        handler.removeMessages(MSG_UPDATE_INFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mHandlerThread.quit();
    }

    @OnClick({R.id.tv, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                break;
            case R.id.btn:
                break;
        }
    }
}
