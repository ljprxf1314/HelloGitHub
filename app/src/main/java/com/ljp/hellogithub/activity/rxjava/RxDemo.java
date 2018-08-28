package com.ljp.hellogithub.activity.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxDemo {

    public static void main(String[] args){
        //第一步创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {

            }
        });
    }
}
