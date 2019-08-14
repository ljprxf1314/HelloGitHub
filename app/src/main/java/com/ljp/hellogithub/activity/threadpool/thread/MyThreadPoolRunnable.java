package com.ljp.hellogithub.activity.threadpool.thread;

import android.util.Log;

/**
 * Created by Administrator on 2019/8/14.
 */

public class MyThreadPoolRunnable implements Runnable {

    private int index = 0;

    public MyThreadPoolRunnable(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            Log.e("MyRunnable",Thread.currentThread().getName()+"线程开始了"+index);
            Thread.sleep(500);
            Log.e("MyRunnable",Thread.currentThread().getName()+"线程结束了"+index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
