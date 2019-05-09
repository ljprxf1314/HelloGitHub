package com.ljp.hellogithub.activity.rxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/4/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxJava06 {

    private String TAG = "RxJava06";


    /**
     * 一是从数量上进行治理, 减少发送进水缸里的事件
       二是从速度上进行治理, 减缓事件发送进水缸的速度
     */
    public void demo1(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
               for (int i=0;;i++){
                   emitter.onNext(i);
               }
            }
        }).subscribeOn(Schedulers.io()).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer o) throws Exception {
                return o%10==0;
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer o) throws Exception {
                        Log.e(TAG,o+"");
                    }
                });
    }

    public void demo2(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i=0;;i++){
                    emitter.onNext(i);
                }
            }
        }).sample(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer o) throws Exception {
                        Log.e(TAG,o+"");
                    }
                });
    }

    public void demo3(){
        Observable<Integer> o1= Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i=0;;i++){
                    emitter.onNext(i);
                    Thread.sleep(2000);
                }
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> o2= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(o1, o2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer+s;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                Log.e(TAG,o);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG,throwable.toString());
            }
        });
    }
}
