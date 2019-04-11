package com.ljp.hellogithub.activity.rxjava;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ljp.hellogithub.activity.rxjava.http.Api;
import com.ljp.hellogithub.activity.rxjava.http.LoginRequest;
import com.ljp.hellogithub.activity.rxjava.http.RetrofitClient;
import com.ljp.hellogithub.net.RestCreate;
import com.ljp.hellogithub.net.RestService;
import com.ljp.hellogithub.util.MD5Util;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/4/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxJava02 {

    public String TAG = "RxJava02";

    /**
     * subscribeOn() 指定的是上游发送事件的线程, observeOn() 指定的是下游接收事件的线程.
     * 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
     * 多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
     *
     * rxjava内置的线程选择:
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
       Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
       Schedulers.newThread() 代表一个常规的新线程
       AndroidSchedulers.mainThread()  代表Android的主线程
     */
    public void demo1(){
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Log.e(TAG, "Observable thread is : " +Thread.currentThread().getName());
                emitter.onNext(1);
            }
        });
        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e(TAG, "Observer thread is :" +Thread.currentThread().getName());
                Log.e(TAG,o.toString());
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    public void login(final Context mContext){
        LoginRequest request = new LoginRequest();
        request.setLogincode("13683310026");
        request.setPwd(MD5Util.md5("123456"));

        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("logincode","13683310026");
        params.put("pwd",MD5Util.md5("123456"));

        Api api = RetrofitClient.create().create(Api.class);
        api.login("/mobile/work/loginControl/login.action",params)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG,value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
