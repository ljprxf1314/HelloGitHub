package com.ljp.hellogithub.activity.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.mvp.presenter.LoginPresenter;
import com.ljp.hellogithub.activity.mvp.view.LoginView;
import com.ljp.hellogithub.activity.rxjava.http.RetrofitClient;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.ISuccess;

import java.util.HashMap;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpActivity extends BaseActivity implements LoginView{

    @BindView(R.id.account_iv)
    ImageView mAccountIv;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.ll_account_right)
    LinearLayout mLlAccountRight;
    @BindView(R.id.account_ll)
    RelativeLayout mAccountLl;
    @BindView(R.id.password_iv)
    ImageView mPasswordIv;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.iv_eye)
    ImageView mIvEye;
    @BindView(R.id.ll_eye)
    LinearLayout mLlEye;
    @BindView(R.id.password_ll)
    RelativeLayout mPasswordLl;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;


    LoginPresenter mLoginPresenter;
    private WeakHashMap<String,String> params = new WeakHashMap<>();

    private boolean isShowPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(this,this);
    }

    @OnClick({R.id.ll_eye, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_eye:
                isShowPassword = !isShowPassword;
                showPassword();
                break;
            case R.id.login_btn:
                params.put("logincode",mEtAccount.getText().toString());
                params.put("pwd",mEtPwd.getText().toString());
                mLoginPresenter.login(params);
                break;
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(String str) {
        showToast(str);
    }

    @Override
    public void onLoginResult(String result) {
        showToast("登录成功");
        Log.e("login",result);
    }

    private void showPassword() {
        if (isShowPassword) {
            mIvEye.setBackgroundResource(R.mipmap.i_login_eye_press);
            mEtPwd.setInputType(0x90);
            mEtPwd.setSelection(mEtPwd.getText().length());
        } else {
            mIvEye.setBackgroundResource(R.mipmap.i_login_eye_normal);
            mEtPwd.setInputType(0x81);
            mEtPwd.setSelection(mEtPwd.getText().length());
        }
    }
}
