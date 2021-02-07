package com.ljp.hellogithub.ui.recycler.complex_grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.ui.recycler.adapter.ComplexGridAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplexGridActivity extends BaseActivity {

    @BindView(R.id.rv_complex_grid)
    RecyclerView mRvComplexGrid;

    private ComplexGridAdapter adapter;


    private List<String> listGoodsName = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_grid);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        adapter = new ComplexGridAdapter();

        for (int i=0;i<40;i++){
            listGoodsName.add("商品:"+i);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return i == 0? 3 : 1;
                // 表示第 0 个 item 占 3 列（即占一整行），其他 item 占一列
            }
        });
        mRvComplexGrid.setLayoutManager(gridLayoutManager);

        adapter.setList(listGoodsName);

        mRvComplexGrid.setAdapter(adapter);

    }

}
