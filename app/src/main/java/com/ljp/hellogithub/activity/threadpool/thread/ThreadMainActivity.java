package com.ljp.hellogithub.activity.threadpool.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2019/8/13.
 */

public class ThreadMainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_main);

        initData();
    }

    private void initData(){
        //1.第一种开启线程方式
        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };
        //第二种通过Runable方式
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread1.start();

        MyRunable myRunable = new MyRunable();
        new Thread(myRunable).start();

        //第三种方式
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.e("futureTask","开始");
                Thread.sleep(3000);
                Log.e("futureTask","结束");
                return "运行结束了";
            }
        });
        new Thread(futureTask).start();
//        try {
//            showToast(futureTask.get().toString());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

    }

    public class MyRunable implements Runnable{
        @Override
        public void run() {

        }
    }
}
