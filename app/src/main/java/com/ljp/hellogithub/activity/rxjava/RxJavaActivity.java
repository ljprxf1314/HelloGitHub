package com.ljp.hellogithub.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RxJavaActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.tv_rx_operators)
    TextView mTvRxOperators;

    private String TAG = "RxJavaActivity";

    RxJava01 mRxJava01 = new RxJava01();
    RxJava02 mRxJava02 = new RxJava02();
    RxJava03Map mRxJava03 = new RxJava03Map();
    RxJava04Zip mRxJava04 = new RxJava04Zip();
    RxJava05 mRxJava05 = new RxJava05();
    RxJava06 mRxJava06 = new RxJava06();
    RxJava07 mRxJava07 = new RxJava07();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn1, R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                mRxJava01.helloRxJava();
                break;
            case R.id.btn2:
                mRxJava02.login(this);
                break;
            case R.id.btn3:
                mRxJava03.loginToBriefing(this);
                break;
            case R.id.btn4:
                mRxJava04.zip();
                break;
            case R.id.btn5:
                mRxJava05.zip();
                break;
            case R.id.btn6:
                mRxJava06.demo3();
                break;
            case R.id.btn7:
                mRxJava07.demo1();
                break;
        }
    }



     /*Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> e) throws Exception {
            mTvRxOperators.append("Observable emit 1" + "\n");
            Log.e(TAG, "Observable emit 1" + "\n");
            e.onNext(1);
            mTvRxOperators.append("Observable emit 2" + "\n");
            Log.e(TAG, "Observable emit 2" + "\n");
            e.onNext(2);
            mTvRxOperators.append("Observable emit 3" + "\n");
            Log.e(TAG, "Observable emit 3" + "\n");
            e.onNext(3);
            e.onComplete();
            mTvRxOperators.append("Observable emit 4" + "\n");
            Log.e(TAG, "Observable emit 4" + "\n" );
            e.onNext(4);
        }
    }).subscribe(new Observer<Integer>() {
        private int i;
        private Disposable mDisposable;

        @Override
        public void onSubscribe(Disposable d) {
            mTvRxOperators.append("onSubscribe : " + d.isDisposed() + "\n");
            Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
            mDisposable = d;
        }

        @Override
        public void onNext(Integer integer) {
            mTvRxOperators.append("onNext : value : " + integer + "\n");
            Log.e(TAG, "onNext : value : " + integer + "\n");
            i++;
            if (i == 2) {
                // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                mDisposable.dispose();
                mTvRxOperators.append("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
            }
        }

        @Override
        public void onError(Throwable e) {
            mTvRxOperators.append("onError : value : " + e.getMessage() + "\n");
            Log.e(TAG, "onError : value : " + e.getMessage() + "\n");
        }

        @Override
        public void onComplete() {
            mTvRxOperators.append("onComplete" + "\n");
            Log.e(TAG, "onComplete" + "\n");
        }
    });*/
}
