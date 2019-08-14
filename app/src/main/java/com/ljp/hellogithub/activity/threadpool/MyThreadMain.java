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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_asynctask, R.id.btn_intentservice, R.id.btn_threadpool,R.id.btn_handlerthread,R.id.btn_thread})
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
}
