package com.ljp.hellogithub.activity.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.bean.RequestLoginBean;
import com.ljp.hellogithub.bean.UserBean;
import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.RestCreate;
import com.ljp.hellogithub.net.RestService;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.ISuccess;
import com.ljp.hellogithub.net.rx.RxRestClient;
import com.ljp.hellogithub.util.MD5Util;

import java.io.IOException;
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
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

    @BindView(R.id.btn_get)
    Button mBtnGet;
    @BindView(R.id.btn_post)
    Button mBtnPost;

    private String TAG = "NetworkMainActivity";

    OkHttpClient client = new OkHttpClient();

    private String url = "https://yxtest.sqkx.net";


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


    @OnClick({R.id.btn_get, R.id.btn_post, R.id.btn_login, R.id.btn_login_rx,R.id.btn_retrofit})
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
                onCallRx();
                //                                onCallRxRestClient();
                break;
            case R.id.btn_get:
                try {
                    getRun();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_post:
                try {
                    postStr();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //                RequestLoginBean bean = new RequestLoginBean();
                //                bean.logincode="13683310026";
                //                bean.pwd=MD5Util.md5("123456");

                //                FormBody body = new FormBody.Builder()
                //                        .add("logincode", "13683310026")
                //                        .add("pwd", MD5Util.md5("123456"))
                //                        .build();
                //
                //                Observable<String> observable = RestCreate.getRxRestService().postRaw(login, body);
                //                observable.subscribeOn(Schedulers.io())
                //                        .observeOn(AndroidSchedulers.mainThread())
                //                        .subscribe(new Observer<String>() {
                //                            @Override
                //                            public void onSubscribe(Disposable d) {
                //
                //                            }
                //
                //                            @Override
                //                            public void onNext(String s) {
                //                                try {
                //                                    showToast(s);
                //                                } catch (Exception e) {
                //                                    e.printStackTrace();
                //                                }
                //                            }
                //
                //                            @Override
                //                            public void onError(Throwable e) {
                //                                showToast("登录失败");
                //                            }
                //
                //                            @Override
                //                            public void onComplete() {
                //                                showToast("请求结束");
                //                            }
                //                        });
                break;
            case R.id.btn_retrofit:
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://yxtest.sqkx.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                RestService service = retrofit.create(RestService.class);
                Call<UserBean> call = service.postLogin(login, params);
                call.enqueue(new Callback<UserBean>() {
                    @Override
                    public void onResponse(Call<UserBean> call, retrofit2.Response<UserBean> response) {
                        if (response.isSuccessful()){
                            showToast("登录成功");
                            showToast(response.body().getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserBean> call, Throwable t) {
                        showToast("请求失败");
                    }
                });
                break;
        }
    }

    //TODO 第一种请求方法
    private void onCallRx() {
        Observable<String> observable = RestCreate.getRxRestService().post(login, params);
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
    private void onCallRxRestClient() {
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

    /**
     * get请求
     *
     * @return
     * @throws IOException
     */
    private void getRun() throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.e(TAG, response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * post提交json数据
     *
     * @param json
     * @return
     * @throws IOException
     */
    private void postRun(String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.e(TAG, response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * post键值对请求
     *
     * @return
     */
    private void postStr() throws IOException {
        final FormBody fromBody = new FormBody.Builder()
                .add("", "")
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    Request request = new Request.Builder().url(url).post(fromBody).build();
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.e(TAG, response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
