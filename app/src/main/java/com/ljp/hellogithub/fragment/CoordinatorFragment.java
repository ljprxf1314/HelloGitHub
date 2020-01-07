package com.ljp.hellogithub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.adapter.RecyclerListAdapter;
import com.ljp.hellogithub.base.BaseFragment;
import com.ljp.hellogithub.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2020/1/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CoordinatorFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    RecyclerListAdapter adapter;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coordinator, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {
        initListData();

        adapter = new RecyclerListAdapter();
        adapter.setDataList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(getActivity(),1));
        mRecyclerView.setAdapter(adapter);
    }

    private void initListData(){
        list = new ArrayList<>();
        for (int i=0;i<50;i++){
            list.add("item:"+(i+1));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
