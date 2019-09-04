package com.ljp.hellogithub.activity.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.bean.BriefingBean;
import com.ljp.hellogithub.bean.NewVersionBean;
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
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

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
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
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
    private String queryVersion = "/mobile/work/loginControl/getNewVersion.action";

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_main);
        ButterKnife.bind(this);

        params.put("logincode", "13683310026");
        params.put("pwd", MD5Util.md5("123456"));
    }


    @OnClick({R.id.btn_get, R.id.btn_post, R.id.btn_login, R.id.btn_login_rx, R.id.btn_retrofit})
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
                //                try {
                //                    postStr();
                //                } catch (IOException e) {
                //                    e.printStackTrace();
                //                }

                //                RequestLoginBean bean = new RequestLoginBean();
                //                bean.logincode = "13683310026";
                //                bean.pwd = MD5Util.md5("123456");

                //                FormBody body = new FormBody.Builder()
                //                        .add("logincode", "13683310026")
                //                        .add("pwd", MD5Util.md5("123456"))
                //                        .build();

//                FormBody body = new FormBody.Builder()
//                        .add("apptype", "android")
//                        .add("source", "5")
//                        .add("curVersion", "20")
//                        .build();

                Observable<String> observable = RestCreate.getRxRestService().post(login, params);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String s) {
                                try {
                                    showToast(s);
                                    Log.e(TAG,s);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG,"转换失败");
                                    showToast("转换失败");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                showToast("登录失败");
                                Log.e(TAG,"出现异常");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
                break;
            case R.id.btn_retrofit:

                //            {"code":200,"data":{"avatar":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epmcDsW98VNeNzic20gokgIeHicnfzcpT3RgFxMGQICTQ5rkicKJoICZI19Le42DOTGJtIw1HNFYGFnA/132",
                // "carid":"100001190417113240251519302","carid_nameref":"另一个车仓库","count":0,"id":"999999190417113303616003753","img":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epmcDsW98VNeNzic20gokgIeHicnfzcpT3RgFxMGQICTQ5rkicKJoICZI19Le42DOTGJtIw1HNFYGFnA/132",
                // "logincode":"13683310026","merchantId":0,"name":"冀培","nickname":"冀培","orgid":"100001","shopAddress":"","shopCusTel":"",
                // "shopId":"100001190415092743888168910",
                // "shopList":[{"ms_id":"10000100017","ms_isCheck":false,"ms_name":"鑫鑫惠民超市","ms_orgid":"100001"},
                // {"ms_id":"100001181011182232396130243","ms_isCheck":false,"ms_name":"青县测试商铺","ms_orgid":"100001"},
                // {"ms_id":"100001190415092743888168910","ms_isCheck":false,"ms_name":"北京丰台经销商","ms_orgid":"100001"},
                // {"ms_id":"100001190416135513125353538","ms_isCheck":false,"ms_name":"北京海淀演示店铺","ms_orgid":"100001"},
                // {"ms_id":"100001190605110220022054582","ms_isCheck":false,"ms_name":"北京朝阳经销商","ms_orgid":"100001"}],
                // "shopName":"北京丰台经销商","token":"46b9ee1aa1e94c5dbcd139434b9e4e1c","userTel":"","userid":"100001180403190200322800100",
                //                    "usertype":"2005","workid":"999999190417113303616003753"},"desc":"登录成功","httpCode":"OK"}

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://yxtest.sqkx.net")
                        .client(OK_HTTP_CLIENT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();


                WeakHashMap<String, Object> map = new WeakHashMap<>();
                map.put("apptype", "android");
                map.put("source", "5");
                map.put("curVersion", "20");


                RestService service = retrofit.create(RestService.class);
                Call<NewVersionBean> call = service.postGson(queryVersion, map);
                call.enqueue(new Callback<NewVersionBean>() {
                    @Override
                    public void onResponse(Call<NewVersionBean> call, retrofit2.Response<NewVersionBean> response) {
                        if (response.isSuccessful()) {
                            showToast("登录成功");
                            showToast(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewVersionBean> call, Throwable t) {
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


    private final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    //获得请求信息，此处如有需要可以添加headers信息
                    Request request = chain.request();
                    //添加Cookie信息
                    request.newBuilder().addHeader("Cookie", "aaaa");
                    //打印请求信息
                    syso("url:" + request.url());
                    syso("method:" + request.method());
                    syso("request-body:" + request.body().toString());

                    //记录请求耗时
                    long startNs = System.nanoTime();
                    okhttp3.Response response;
                    try {
                        //发送请求，获得相应，
                        response = chain.proceed(request);
                    } catch (Exception e) {
                        throw e;
                    }
                    long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
                    //打印请求耗时
                    syso("耗时:" + tookMs + "ms");
                    //使用response获得headers(),可以更新本地Cookie。
                    syso("headers==========");
                    Headers headers = response.headers();
                    syso(headers.toString());

                    //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
                    ResponseBody responseBody = response.body();

                    //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    //获得返回的数据
                    Buffer buffer = source.buffer();
                    //使用前clone()下，避免直接消耗
                    syso("response:" + buffer.clone().readString(Charset.forName("UTF-8")));
                    return response;
                }
            })
            .build();

    private static void syso(String msg) {
        System.out.println(msg);
    }

}
