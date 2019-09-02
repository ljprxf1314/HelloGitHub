package com.ljp.hellogithub.net.rx;

import android.content.Context;

import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.IFailure;
import com.ljp.hellogithub.net.callback.IProgress;
import com.ljp.hellogithub.net.callback.IRequest;
import com.ljp.hellogithub.net.callback.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/2
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxRestClientBuilder<T> {

    private WeakHashMap<String, Object> mParams = new WeakHashMap<>();//请求参数
    private String mUrl = null;
    private RequestBody mBody = null;
    private boolean isLoading = false;
    private String mDialog = null;//加载时显示文字
    private File mFile = null;
    private Context mContext = null;
    private Class<T> mClass;
    private IProgress mProgress;

    public RxRestClientBuilder() {
    }

    public RxRestClientBuilder(Class<T> mClass) {
        this.mClass = mClass;
    }

    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(String key, String value){
        mParams.put(key,value);
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, String mDialog) {
        this.isLoading = true;
        this.mDialog = mDialog;
        this.mContext = context;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.isLoading = true;
        this.mContext = context;
        return this;
    }

//    public final RestClientBuilder mClass(Class<T> mClass){
//        this.mClass = mClass;
//        return this;
//    }

    public final RxRestClientBuilder progress(IProgress mProgress){
        this.mProgress = mProgress;
        return this;
    }


    public final RxRestClient build(){
        return new RxRestClient<>(mParams,mUrl,
                mBody, mClass,isLoading,mDialog,mFile, mContext,mProgress);
    }

}
