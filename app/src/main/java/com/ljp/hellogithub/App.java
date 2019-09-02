package com.ljp.hellogithub;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;

import com.ljp.hellogithub.activity.threadpool.DownloadConfig;
import com.ljp.hellogithub.activity.threadpool.DownloadManager;
import com.ljp.hellogithub.activity.threadpool.db.DownloadHelper;
import com.ljp.hellogithub.activity.threadpool.file.FileStorageManager;
import com.ljp.hellogithub.activity.threadpool.http.HttpManager;
import com.ljp.hellogithub.util.LaunchTimer;
import com.tencent.mmkv.MMKV;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/9
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class App extends Application {

    public static Context context = null;
    public static Handler handler = null;
    public static Thread mainThread = null;
    public static int mainThreadId = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LaunchTimer.startRecord();
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Debug.startMethodTracing("APP");

        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();

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
        //腾讯存储框架 MMKV
        MMKV.initialize(this);

//        Debug.stopMethodTracing();
    }
}
