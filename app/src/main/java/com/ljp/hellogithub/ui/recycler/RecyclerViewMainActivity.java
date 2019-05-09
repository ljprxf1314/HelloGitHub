package com.ljp.hellogithub.ui.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.ui.recycler.tworecyclerlist.RecyclerViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/5/9
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RecyclerViewMainActivity extends BaseActivity {

    @BindView(R.id.btn_recycler)
    Button mBtnRecycler;
    @BindView(R.id.btn_decoration)
    Button mBtnDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_recycler, R.id.btn_decoration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recycler://二级嵌套样式
                startActivity(RecyclerViewActivity.class);
                break;
            case R.id.btn_decoration:
                startActivity(RecyclerItemDecorationStyleActivity.class);
                break;
        }
    }
}
