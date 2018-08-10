package com.ljp.hellogithub;

import android.app.Application;

import com.ljp.hellogithub.activity.threadpool.DownloadConfig;
import com.ljp.hellogithub.activity.threadpool.DownloadManager;
import com.ljp.hellogithub.activity.threadpool.db.DownloadHelper;
import com.ljp.hellogithub.activity.threadpool.file.FileStorageManager;
import com.ljp.hellogithub.activity.threadpool.http.HttpManager;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/9
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FileStorageManager.getInstance().init(this);
        DownloadHelper.getInstance().init(this);
        HttpManager.getInstance().init(this);
//        Stetho.initializeWithDefaults(this);

        DownloadConfig config = new DownloadConfig.Builder()
                .setCoreThreadSize(2)
                .setMaxThreadSize(4)
                .setLocalProgressThreadSize(1)
                .builder();
        DownloadManager.getInstance().init(config);

    }
}
