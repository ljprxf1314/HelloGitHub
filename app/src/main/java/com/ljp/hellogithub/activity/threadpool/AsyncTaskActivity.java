package com.ljp.hellogithub.activity.threadpool;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

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

    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        ButterKnife.bind(this);

        myAsyncTask = new MyAsyncTask(mProgressBar);
    }


    @OnClick({R.id.btn_start, R.id.btn_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                myAsyncTask.execute();
                break;
            case R.id.btn_end:
                myAsyncTask.cancel(true);
                break;
        }
    }
}
