package com.ljp.hellogithub.activity.mvp.presenter;

/**
 * Created by lijipei on 2020/3/23.
 */

public interface OnLoginFinishedListener {

    void success(String result);

    void error(String str);

}
