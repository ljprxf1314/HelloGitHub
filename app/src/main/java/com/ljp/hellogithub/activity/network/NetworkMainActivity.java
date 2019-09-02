package com.ljp.hellogithub.activity.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.RestCreate;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.ISuccess;
import com.ljp.hellogithub.net.rx.RxRestClient;
import com.ljp.hellogithub.util.MD5Util;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lijipei on 2019/9/2.
 * 网络主界面
 */

@SuppressWarnings("ALL")
public class NetworkMainActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    WeakHashMap<String, Object> params = new WeakHashMap<>();
    private String login = "/mobile/work/loginControl/login.action";

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_main);
        ButterKnife.bind(this);

        params.put("logincode", "13683310026");
        params.put("pwd", MD5Util.md5("123456"));
    }


    @OnClick({R.id.btn_login,R.id.btn_login_rx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                RestClient.builder(String.class)
                        .url(login)
                        .loader(this)
                        .params(params)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(Object obj) {
                                showToast("登录成功");
                            }
                        })
                        .error(new IError() {
                            @Override
                            public void onError(int code, String msg) {
                                showToast("登录失败");
                            }
                        })
                        .build()
                        .post();
                break;
            case R.id.btn_login_rx:
//                onCallRx();
                onCallRxRestClient();
                break;
        }
    }

    //TODO 第一种请求方法
    private void onCallRx(){
        Observable<String> observable = RestCreate.getRxRestService().post(login,params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToast(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("登录失败");
                    }

                    @Override
                    public void onComplete() {
                        showToast("请求结束");
                    }
                });
    }

    //TODO 第二种网络请求
    private void onCallRxRestClient(){
        RxRestClient.builder()
                .url(login)
                .params(params)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToast(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
