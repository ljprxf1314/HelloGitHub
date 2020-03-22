package com.ljp.hellogithub.activity.mvp.presenter.veiw;

/**
 * Created by Administrator on 2020/3/22.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void onError(String str);
}
