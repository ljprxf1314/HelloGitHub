package com.ljp.hellogithub.ui.recycler.tworecyclerlist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljp.hellogithub.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/7/9
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RecyclerOneAdapter extends RecyclerView.Adapter {

    private static final String tag = "RecyclerOne";
    private int count = 0;


    //创建item界面
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_one, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        //单个商家的商品列表不需要滑动，所以在这里禁止掉商品item的滑动事件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.VERTICAL,
                false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        holder.childAdapter = new RecyclerGoodsChildAdapter();
        holder.mRvChild.setLayoutManager(linearLayoutManager);
        holder.mRvChild.setAdapter(holder.childAdapter);
        //下面两句是防止刷新商品的recyclerView导致商家recyclerView也发生滑动
        holder.mRvChild.setFocusableInTouchMode(false);
        holder.mRvChild.requestFocus();

        Log.e(tag,"CreateViewHolder");
        count++;
        Log.e(tag,"count:"+count);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e(tag,"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return 13;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rv_child)
        RecyclerView mRvChild;
        @BindView(R.id.tv_btn)
        TextView mTvBtn;
        RecyclerGoodsChildAdapter childAdapter;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
