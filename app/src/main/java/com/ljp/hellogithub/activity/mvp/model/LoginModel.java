package com.ljp.hellogithub.activity.mvp.model;

import com.ljp.hellogithub.activity.mvp.presenter.OnLoginFinishedListener;

import java.util.WeakHashMap;

/**
 * Created by lijipei on 2020/3/23.
 */

public interface LoginModel {

    void login(WeakHashMap<String,String> params, OnLoginFinishedListener listener);
}
