package com.ljp.hellogithub.activity.rxjava;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ljp.hellogithub.activity.rxjava.http.Api;
import com.ljp.hellogithub.activity.rxjava.http.RetrofitClient;
import com.ljp.hellogithub.activity.rxjava.request.LoginRequest;
import com.ljp.hellogithub.bean.BaseBean;
import com.ljp.hellogithub.util.JsonUtils;
import com.ljp.hellogithub.util.MD5Util;
import com.ljp.hellogithub.util.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/4/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxJava03Map {

    public String TAG = "RxJava03";

    /**
     * map标签:将圆形事件转换为矩形事件, 从而导致下游接收到的事件就变为了矩形
     */
    public void demoMap(){
       Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               emitter.onNext(1);
               emitter.onNext(2);
               emitter.onNext(3);
           }
       }).map(new Function<Integer,String>() {
           @Override
           public String apply(Integer o) throws Exception {
               return "This is result:"+o;
           }
       }).subscribe(new Consumer<String>() {
           @Override
           public void accept(String o) throws Exception {
               Log.e(TAG,o);
           }
       });
    }

    /**
     * FlatMap:将一个发送事件的上游Observable变换为多个发送事件的Observables，
     *         然后将它们发射的事件合并后放进一个单独的Observable里.
     *         flatMap并不保证事件的顺序
     */
    public void demoFlatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10,  TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    //  /mobile/work/reportControl/getBriefing.action

    /**
     * 登录成功后请求营收简报数据
     * @param mContext
     */
    public void loginToBriefing(final Context mContext){
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("logincode","13683310026");
        params.put("pwd",MD5Util.md5("123456"));

        final WeakHashMap<String, Object> paramsBriefing = new WeakHashMap<>();
        paramsBriefing.put("deviceType","android");
        paramsBriefing.put("work_id","999999180516184533970836572");
        paramsBriefing.put("deviceType","android");
        paramsBriefing.put("endDate","2019-04-15");
        paramsBriefing.put("mb_id","100001180511151732045805841");
        paramsBriefing.put("userId","999999180516184533970836572");
        paramsBriefing.put("orgid","100001");
        paramsBriefing.put("token","d2115c1e9c654f42a49040d8264e96f9");
        paramsBriefing.put("shopid","100001181011182232396130243");
        paramsBriefing.put("userType","1");
        paramsBriefing.put("startDate","2019-04-15");

        final Api api = RetrofitClient.create().create(Api.class);
        api.post("/mobile/work/loginControl/login.action",params) //发起登录请求
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG,"登录信息:"+s);
                        BaseBean bean = JsonUtils.parseObject(s, BaseBean.class);
                        if ("200".equals(bean.getCode())){
                            UIUtils.Toast("登录成功");
                        }else{
                            UIUtils.Toast("登录失败");
                        }
                    }
                }).observeOn(Schedulers.io()) //回到IO线程去请求
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return api.post("/mobile/work/reportControl/getBriefing.action",paramsBriefing);
                    }
                }).observeOn(AndroidSchedulers.mainThread())//在主线程处理
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e(TAG,o.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable o) throws Exception {
                        Log.e(TAG,o.toString());
                    }
                });
    }
}
