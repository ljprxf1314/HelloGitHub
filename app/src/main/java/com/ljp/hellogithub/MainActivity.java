package com.ljp.hellogithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.activity.textview.TextViewSpannableActivity;
import com.ljp.hellogithub.activity.viewclicksystem.MyViewActivity;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_activity)
    Button mBtnActivity;
    @BindView(R.id.btn_my_view)
    Button mBtnMyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_activity, R.id.btn_my_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_activity:
                startActivity(TextViewSpannableActivity.class);
                break;
            case R.id.btn_my_view:
                startActivity(MyViewActivity.class);
                break;
        }
    }
}
