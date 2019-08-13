package com.ljp.hellogithub.activity.message_mechanism;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lijipei on 2019/8/12.
 */

public class MessageMainActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.tv1)
    TextView mTv1;

    String TAG = "MessageMainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message);
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "step 0 ");
                Looper.prepare();
                Toast.makeText(MessageMainActivity.this, "run on thread", Toast.LENGTH_SHORT);
                Log.e(TAG, "step 1 ");
                Looper.loop();
                Log.e(TAG, "step 2 ");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mTv1.setText("run on thread update textview!");
            }
        }).start();
    }

    @OnClick(R.id.btn1)
    public void onViewClicked() {
        startActivity(MessageMechanismActivity.class);
    }
}
