package com.ljp.hellogithub.ui.coordinator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.adapter.RecyclerListAdapter;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/6/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CoordinatorLayoutActivity02 extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    RecyclerListAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout02);
        ButterKnife.bind(this);

        initListData();

        adapter = new RecyclerListAdapter();
        adapter.setDataList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(this,1));
        mRecyclerView.setAdapter(adapter);
    }

    private void initListData(){
        list = new ArrayList<>();
        for (int i=0;i<50;i++){
            list.add("item:"+(i+1));
        }
    }
}
