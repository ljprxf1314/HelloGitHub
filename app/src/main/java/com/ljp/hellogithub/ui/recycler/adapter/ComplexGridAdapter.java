package com.ljp.hellogithub.ui.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljp.hellogithub.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplexGridAdapter extends RecyclerView.Adapter<ComplexGridAdapter.MyViewHolder> {

    private static final String tag = "ComplexGridAdapter";
    List<String> list;

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_complex_recycler, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.mTvName.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
