package com.ljp.hellogithub.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.ui.constraint.ConstraintLayoutActivity;
import com.ljp.hellogithub.ui.recycler.RecyclerViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/6/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class UIActivity extends BaseActivity {

    @BindView(R.id.btn_constraint)
    Button mBtnConstraint;
    @BindView(R.id.btn_recycler)
    Button mBtnRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_constraint,R.id.btn_recycler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_constraint:
                startActivity(ConstraintLayoutActivity.class);
                break;
            case R.id.btn_recycler:
                startActivity(RecyclerViewActivity.class);
                break;
        }
    }
}
