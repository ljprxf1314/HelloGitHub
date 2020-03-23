package com.ljp.hellogithub.activity.mvp.presenter;

import android.content.Context;

import com.ljp.hellogithub.activity.mvp.model.LoginModel;
import com.ljp.hellogithub.activity.mvp.model.impl.LoginModelImpl;
import com.ljp.hellogithub.activity.mvp.view.LoginView;
import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.ISuccess;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2020/3/22.
 */

public class LoginPresenter extends BasePresenter<LoginView> implements OnLoginFinishedListener {

    public LoginView mLoginView;
    public LoginModel mLoginModel;

    public LoginPresenter(Context context, LoginView loginView) {
        super(context);
        mLoginView = loginView;
        this.mLoginModel = new LoginModelImpl(mContext);
    }

    public void login(WeakHashMap<String,String> params){
        mLoginModel.login(params,this);
    }

    @Override
    public void success(String result) {
        mLoginView.onLoginResult(result);
    }

    @Override
    public void error(String str) {
        mLoginView.onError(str);
    }
}
