package com.ljp.hellogithub.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/5/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class HelloService extends Service {

    private String TAG = "HelloService";

    /**
     * 标识服务如果被杀死之后的行为
     */
    int mStartMode;

    /**
     * 绑定的客户端接口
     */
    IBinder mBinder;

    /**
     * 标识是否可以使用onRebind
     */
    boolean mAllowRebind;

    /**
     * 当服务被创建时调用.
     */
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    /**
     * 调用startService()启动服务时回调
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        Toast.makeText(this, "服务已经启动", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 通过bindService()绑定到服务的客户端
     */
    @Override
    public MyBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        MyBinder myBinder = new MyBinder();
        return myBinder;
    }

    public class MyBinder extends Binder {
        public void systemOut() {
            Log.e(TAG, "该方法在MyService的内部类MyBinder中");
        }

        public void startMusice() {
            Log.e(TAG, "开始播放音乐");
        }

        public void stopMusice() {
            Log.e(TAG, "结束播放音乐");
        }

    }

    /**
     * 通过unbindService()解除所有客户端绑定时调用
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return mAllowRebind;
    }

    /**
     * 通过bindService()将客户端绑定到服务时调用
     */
    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
    }

    /**
     * 服务不再有用且将要被销毁时调用
     */
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        Toast.makeText(this, "服务已经停止", Toast.LENGTH_LONG).show();
    }
}
