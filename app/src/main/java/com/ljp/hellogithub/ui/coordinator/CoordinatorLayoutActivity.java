package com.ljp.hellogithub.ui.coordinator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.adapter.RecyclerListAdapter;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2020/1/3
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CoordinatorLayoutActivity extends BaseActivity {

    RecyclerListAdapter adapter;
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn1:
                startActivity(CoordinatorLayoutActivity01.class);
                break;
            case R.id.btn2:
                startActivity(CoordinatorLayoutActivity02.class);
                break;
            case R.id.btn3:
                startActivity(CoordinatorLayoutActivity03.class);
                break;
        }
    }
}
