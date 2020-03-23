package com.ljp.hellogithub.activity.mvp.model.impl;

import android.content.Context;

import com.ljp.hellogithub.activity.mvp.model.LoginModel;
import com.ljp.hellogithub.activity.mvp.presenter.OnLoginFinishedListener;
import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.ISuccess;

import java.util.WeakHashMap;

/**
 * Created by lijipei on 2020/3/23.
 */

public class LoginModelImpl implements LoginModel{

    private Context mContext;

    public LoginModelImpl(Context context) {
        mContext = context;
    }

    private String login = "/mobile/work/loginControl/login.action";

    @Override
    public void login(WeakHashMap<String, String> params, final OnLoginFinishedListener listener) {
        RestClient.builder(String.class)
                .url(login)
                .loader(mContext)
                .params(params)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(Object obj) {
                        listener.success(obj.toString());
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        listener.error(msg);
                    }
                })
                .build()
                .post();
    }
}
