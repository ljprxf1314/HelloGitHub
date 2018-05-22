package com.ljp.hellogithub.activity.viewclicksystem;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyViewActivity extends BaseActivity{

    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        String str = String.valueOf(ViewConfiguration.get(this).getScaledTouchSlop());
        showToast(str);

        ObjectAnimator.ofFloat(mBtn, "translationX", 0, 100).setDuration(100)
                .start();
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(Demo1Activity.class);
                break;
            case R.id.btn2:
                startActivity(Demo2Activity.class);
                break;
        }
    }
}
