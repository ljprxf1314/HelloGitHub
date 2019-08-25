package com.ljp.hellogithub.activity.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/8/25.
 */

public class NetworkMainActivity extends BaseActivity {

    OkHttpClient client = new OkHttpClient();
    @BindView(R.id.btn_get)
    Button mBtnGet;
    @BindView(R.id.btn_post)
    Button mBtnPost;

    private String TAG = "NetworkMainActivity";

    private String url = "https://yxtest.sqkx.net";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_main);
        ButterKnife.bind(this);


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
                        Log.e(TAG,response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                }catch (IOException e){
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


    @OnClick({R.id.btn_get, R.id.btn_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                break;
        }
    }
}
