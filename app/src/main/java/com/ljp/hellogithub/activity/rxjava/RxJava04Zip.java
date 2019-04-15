package com.ljp.hellogithub.activity.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2019/4/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxJava04Zip {

    public String TAG = "RxJava04";

    /**
     * zip:使用场景。
     * 比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取,
     * 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了:
     */
    public void zip(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "emit 1");

                emitter.onNext(1);
                Thread.sleep(1000);
                Log.e(TAG, "emit 2");
                emitter.onNext(2);
                Thread.sleep(1000);
                Log.e(TAG, "emit 3");
                emitter.onNext(3);
                Thread.sleep(1000);
                Log.e(TAG, "emit 4");
                emitter.onNext(4);
                Thread.sleep(1000);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "emit A");
                emitter.onNext("A");
                Thread.sleep(1000);
                Log.e(TAG, "emit B");
                emitter.onNext("B");
                Thread.sleep(1000);
                Log.e(TAG, "emit C");
                emitter.onNext("C");
                Thread.sleep(1000);
                Log.d(TAG, "emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer o, String o2) throws Exception {
                return o+o2;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }
}
