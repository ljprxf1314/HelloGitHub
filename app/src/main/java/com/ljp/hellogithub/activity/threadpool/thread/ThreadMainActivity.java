package com.ljp.hellogithub.activity.threadpool.thread;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/8/13.
 */

public class ThreadMainActivity extends BaseActivity {

    private final String TAG = "ThreadMainActivity";
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;

    /**
     * 线程池
     */
    private ThreadPoolExecutor mThreadPoolExecutor;
    private int corePoolSize = 2;//核心线程数量
    private int maximumPoolSize = 5;//最大5个线程容量
    private int keepAliveTime = 1000;//非核心线程数量的超时时长,毫秒

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_main);
        ButterKnife.bind(this);

        initData();

        mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            //实现原子性自增
            private AtomicInteger mInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable,"thread name:"+mInteger.getAndIncrement());
                return thread;
            }
        });
        //mThreadPoolExecutor.allowCoreThreadTimeOut(false);//不允许核心线程超时,设置为true时核心线程和普通线程一样会超时
    }

    private void initData() {
        //1.第一种开启线程方式
        new Thread() {
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
                Log.e("futureTask", "开始");
                Thread.sleep(3000);
                Log.e("futureTask", "结束");
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

        //4.线程执行不会按顺序执行
        //        Thread[] threads = new Thread[10];
        //        for (int i=0;i<10;i++){
        //            threads[i] = new RandomThread("thread# "+i);
        //        }
        //        for (int i=0;i < threads.length;i++){
        //            threads[i].start();
        //        }


        Thread[] threads2 = new Thread[5];
        RandomThread2 runnable2 = new RandomThread2();
        for (int i = 0; i < 5; i++) {
            threads2[i] = new Thread(runnable2, "tharead" + i);
        }
        for (int i = 0; i < threads2.length; i++) {
            threads2[i].start();
        }


    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                for (int i=0;i<20;i++){
                    mThreadPoolExecutor.execute(new MyThreadPoolRunnable(i));
                }
                break;
            case R.id.btn2:
                break;
        }
    }

    public class MyRunable implements Runnable {
        @Override
        public void run() {
        }
    }

    public class RandomThread extends Thread {
        public RandomThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(2000);
                Log.e(TAG, Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class RandomThread2 extends Thread {
        private int count = 5;

        @Override
        public synchronized void run() {
            super.run();
            Log.e(TAG, Thread.currentThread().getName() + ":" + count--);
        }
    }
}
