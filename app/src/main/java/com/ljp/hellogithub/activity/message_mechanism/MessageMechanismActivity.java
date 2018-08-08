package com.ljp.hellogithub.activity.message_mechanism;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import java.util.List;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MessageMechanismActivity extends BaseActivity {
    //android消息机制:讲解很透彻的文章
    //http://www.cnblogs.com/codingmyworld/archive/2011/09/12/2174255.html

    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_mechanism);

        mBooleanThreadLocal.set(true);

        Log.e("thread","{thread}mBooleanThreadLocal="+mBooleanThreadLocal.get());

        new Thread("thread1"){
            @Override
            public void run() {
                super.run();
                mBooleanThreadLocal.set(false);
                Log.e("thread1","{thread1}mBooleanThreadLocal="+mBooleanThreadLocal.get());
            }
        }.start();

        new Thread("thread2"){
            @Override
            public void run() {
                super.run();
                Log.e("thread2","{thread2}mBooleanThreadLocal="+mBooleanThreadLocal.get());
            }
        }.start();
    }
}
