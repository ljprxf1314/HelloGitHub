package com.ljp.hellogithub.ui.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.ui.recycler.adapter.RecyclerAdapter;
import com.ljp.hellogithub.ui.recycler.itemdecoration.TimeLineItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/7/6
 *     desc   : recyclerview样式界面
 *     version: 1.0
 * </pre>
 */

public class RecyclerItemDecorationStyleActivity extends BaseActivity {


    @BindView(R.id.rv)
    RecyclerView mRv;

    RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_decoration_style);
        ButterKnife.bind(this);

        mRv.addItemDecoration(new TimeLineItemDecoration(this));
        mRv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter();
        mRv.setAdapter(adapter);
    }




}
