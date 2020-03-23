package com.ljp.hellogithub.activity.mvp.view;

/**
 * Created by Administrator on 2020/3/22.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void onError(String str);
}
