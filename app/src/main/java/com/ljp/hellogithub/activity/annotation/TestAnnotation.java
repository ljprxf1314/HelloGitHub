package com.ljp.hellogithub.activity.annotation;

import android.util.Log;

/**
 * Created by lijipei on 2019/8/22.
 */

public class TestAnnotation {

    private String TAG = "TestAnnotation";

    @Deprecated  //提示超时
    public void say(){
        Log.e(TAG,"Noting has to say!");
    }

    public void speak(){
        Log.e(TAG,"I have a dream!");
    }

}
