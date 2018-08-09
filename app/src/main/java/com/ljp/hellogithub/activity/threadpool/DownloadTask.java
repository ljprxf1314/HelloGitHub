package com.ljp.hellogithub.activity.threadpool;


import com.ljp.hellogithub.activity.threadpool.http.DownloadCallback;

/**
 * Created by Administrator on 2018/4/17.
 */

public class DownloadTask {

    public DownloadTask(String url, DownloadCallback callback) {
        mUrl = url;
        mCallback = callback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DownloadTask that = (DownloadTask) o;

        if (mUrl != null ? !mUrl.equals(that.mUrl) : that.mUrl != null)
            return false;
        return mCallback != null ? mCallback.equals(that.mCallback) : that.mCallback == null;
    }

    @Override
    public int hashCode() {
        int result = mUrl != null ? mUrl.hashCode() : 0;
        result = 31 * result + (mCallback != null ? mCallback.hashCode() : 0);
        return result;
    }

    private String mUrl;

    private DownloadCallback mCallback;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
