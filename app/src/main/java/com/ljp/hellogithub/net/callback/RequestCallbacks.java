package com.ljp.hellogithub.net.callback;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.ljp.hellogithub.net.ExceptionType;
import com.ljp.hellogithub.util.JsonUtils;
import com.ljp.hellogithub.util.Logger;
import com.ljp.hellogithub.util.ProgressUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/3
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RequestCallbacks<T> implements Callback<String> {

    private final IRequest request;
    private final ISuccess success;
    private final IFailure failure;
    private final IError error;
    private final boolean isLoading;
    private final ProgressUtils pu;
    private final Context context;
    private final Class<T> mClass;

    private T mT;//传进来解析的泛型

    public RequestCallbacks(Context context,IRequest request, ISuccess success, IFailure failure, IError error,Class<T> mClass, boolean isLoading, ProgressUtils pu) {
        this.request = request;
        this.success = success;
        this.failure = failure;
        this.error = error;
        this.isLoading = isLoading;
        this.pu = pu;
        this.context = context;
        this.mClass = mClass;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            Logger.error("url",call.request().url().toString());
//            LogUtils.e("params",call.request().body().toString());
            if (call.isExecuted()){
                if (success!=null){
                    try{
                        if (mClass == String.class || mClass == null) {
                            mT = (T) response.body();
                        } else {
                            mT = JsonUtils.parseObject(response.body(), mClass);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error.onError(response.code(), ExceptionType.JsonParseException.getDetail());
                    } catch(Exception e){
                        if (error != null) {
                            error.onError(response.code(), e.toString());
                        }
                    }

                    if (mT == null) {
                        error.onError(response.code(),ExceptionType.Exception.getDetail());
                    } else {
                        success.onSuccess(mT);
                    }
                }
            }
        }else{
            if (error != null) {
                error.onError(response.code(), response.message());
            }
        }

        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (failure!=null){
            failure.onFailure();
        }

        onRequestFinish();
    }

    private void onRequestFinish(){
        if (isLoading && pu!=null){
            pu.dismissDialog(context);
        }

        if (request != null){
            request.onRequestEnd();
        }
    }


}
