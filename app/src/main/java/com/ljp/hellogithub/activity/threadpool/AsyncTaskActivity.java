package com.ljp.hellogithub.activity.threadpool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AsyncTaskActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_end)
    Button mBtnEnd;
    @BindView(R.id.progress_bar2)
    ProgressBar mProgressBar2;
    @BindView(R.id.progress_bar3)
    ProgressBar mProgressBar3;
    @BindView(R.id.progress_bar4)
    ProgressBar mProgressBar4;
    @BindView(R.id.progress_bar5)
    ProgressBar mProgressBar5;
    @BindView(R.id.progress_bar6)
    ProgressBar mProgressBar6;
    @BindView(R.id.progress_bar7)
    ProgressBar mProgressBar7;

    MyAsyncTask myAsyncTask;
    MyAsyncTask myAsyncTask2;
    MyAsyncTask myAsyncTask3;
    MyAsyncTask myAsyncTask4;
    MyAsyncTask myAsyncTask5;
    MyAsyncTask myAsyncTask6;
    MyAsyncTask myAsyncTask7;


    private Executor singleExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        ButterKnife.bind(this);

//        singleExecutor = Executors.newSingleThreadExecutor();
        singleExecutor = Executors.newFixedThreadPool(5);

        myAsyncTask = new MyAsyncTask();
        myAsyncTask2 = new MyAsyncTask();
        myAsyncTask3 = new MyAsyncTask();
        myAsyncTask4 = new MyAsyncTask();
        myAsyncTask5 = new MyAsyncTask();
        myAsyncTask6 = new MyAsyncTask();
        myAsyncTask7 = new MyAsyncTask();
    }


    @OnClick({R.id.btn_start, R.id.btn_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                myAsyncTask.executeOnExecutor(singleExecutor,mProgressBar);
                myAsyncTask2.executeOnExecutor(singleExecutor,mProgressBar2);
                myAsyncTask3.executeOnExecutor(singleExecutor,mProgressBar3);
                myAsyncTask4.executeOnExecutor(singleExecutor,mProgressBar4);
                myAsyncTask5.executeOnExecutor(singleExecutor,mProgressBar5);
                myAsyncTask6.executeOnExecutor(singleExecutor,mProgressBar6);
                myAsyncTask7.executeOnExecutor(singleExecutor,mProgressBar7);
                break;
            case R.id.btn_end:
                myAsyncTask.cancel(true);
                break;
        }
    }
}
