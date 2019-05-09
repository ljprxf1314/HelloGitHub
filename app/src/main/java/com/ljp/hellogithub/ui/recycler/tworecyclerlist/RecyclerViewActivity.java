package com.ljp.hellogithub.ui.recycler.tworecyclerlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.ui.recycler.itemdecoration.SpaceItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/7/6
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RecyclerViewActivity extends BaseActivity {

    @BindView(R.id.btn_recycler)
    Button mBtnRecycler;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.sr_layout)
    SwipeRefreshLayout mSrLayout;

    private RecyclerOneAdapter adapterOne;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置recyclerview的方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapterOne = new RecyclerOneAdapter();
        mRv.setLayoutManager(linearLayoutManager);
        //创建分割线对象，第一个参数为上下文，第二个参数为RecyclerView排列方向
        //        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //设置分割线颜色
        //        decoration.setDrawable(getResources().getDrawable(R.color.black));

        SpaceItemDecoration decoration = new SpaceItemDecoration(this, 10);
//        decoration.setDrawable(R.color.colorPrimary);

        mRv.addItemDecoration(decoration);
        mRv.setAdapter(adapterOne);
        //1.解决scrollview嵌套recyclerview的滑动卡顿问题
        //        mRv.setHasFixedSize(true);
        //        mRv.setNestedScrollingEnabled(false);

        adapterOne.notifyDataSetChanged();

        mSrLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mSrLayout.setRefreshing(false);
                    }
                }).start();
            }
        });
    }

}
