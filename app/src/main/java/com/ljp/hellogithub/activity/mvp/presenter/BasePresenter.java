package com.ljp.hellogithub.activity.mvp.presenter;

import android.content.Context;

import com.ljp.hellogithub.activity.mvp.view.BaseView;

/**
 * Created by Administrator on 2020/3/22.
 */

public abstract class BasePresenter<T extends BaseView> {

    public Context mContext;

    public BasePresenter(Context context) {
        mContext = context;
    }

    public BasePresenter() {
    }

    /*
                检查网络是否可用
             */
    private void checkNetWork(){
//        if(NetWorkUtils.isNetWorkAvailable(context)){
//            return true
//        }
//        mView.onError("网络不可用");
//        return false;
    }
}
