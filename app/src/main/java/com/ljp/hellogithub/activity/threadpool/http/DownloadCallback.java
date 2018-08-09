package com.ljp.hellogithub.activity.threadpool.http;

import java.io.File;

/**
 * Created by Administrator on 2018/3/18.
 * 回调接口
 */

public interface DownloadCallback {

    void success(File file);

    void fail(int errorCode, String errorMessage);

    void progress(int progress);
}
