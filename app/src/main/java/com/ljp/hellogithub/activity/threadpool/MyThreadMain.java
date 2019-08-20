package com.ljp.hellogithub.activity.threadpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.threadpool.thread.ThreadMainActivity;
import com.ljp.hellogithub.base.BaseActivity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/7
 *     desc   : android线程和线程池主页
 *     version: 1.0
 * </pre>
 */

public class MyThreadMain extends BaseActivity {

    @BindView(R.id.btn_countdownlatch)
    Button mBtnCountdownlatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_asynctask, R.id.btn_intentservice, R.id.btn_threadpool, R.id.btn_handlerthread,
            R.id.btn_thread,R.id.btn_countdownlatch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_asynctask:
                startActivity(AsyncTaskActivity.class);
                break;
            case R.id.btn_intentservice:
                Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
                startIntentService();
                break;
            case R.id.btn_handlerthread:
                startActivity(HandlerThreadActivity.class);
                break;
            case R.id.btn_threadpool:
                startActivity(ThreadPoolExecutorActivity.class);
                break;
            case R.id.btn_thread://线程操作方式
                startActivity(ThreadMainActivity.class);
                break;
            case R.id.btn_countdownlatch:
                //视频会议等待类
                VideoConference videoConference = new VideoConference(10);
                Thread thread = new Thread(videoConference);
                thread.start();

                Thread[] threads = new Thread[10];
                for (int i=0;i<10;i++){
                    threads[i] = new Thread(new Participant("P"+i,videoConference));
                }

                for (int i=0;i<10;i++){
                    threads[i].start();
                }
                break;
        }
    }

    private void startIntentService() {
        Intent service = new Intent(this, LocalIntentService.class);
        service.putExtra("task_action", "com.ryg.action.TASK1");
        startService(service);
        service.putExtra("task_action", "com.ryg.action.TASK2");
        startService(service);
        service.putExtra("task_action", "com.ryg.action.TASK3");
        startService(service);

    }


    public class VideoConference implements Runnable{
        private final CountDownLatch controller;

        public VideoConference(int number) {
            controller = new CountDownLatch(number);
        }

        public void arrive(String name) {
            System.out.printf("%s has arrived.\n", name);
            controller.countDown();
            System.out.printf("VideoConference: Waiting for %d participants.\n", controller.getCount());
        }

        @Override
        public void run() {
            System.out.printf("VidwoConference: Initialization: %d participants.\n", controller.getCount());

            try {
                System.out.printf("等待中");
                controller.await();
                System.out.printf("VidwoConference: All the participants have come.\n");
                System.out.printf("VidwoConference: Let's start...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class Participant implements Runnable{
        private String name;
        private VideoConference videoConference;

        public Participant(String name, VideoConference videoConference) {
            this.name = name;
            this.videoConference = videoConference;
        }

        @Override
        public void run() {
            long duration = (long) (Math.random() * 10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            videoConference.arrive(name);
        }
    }

}
