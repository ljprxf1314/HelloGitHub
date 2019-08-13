package com.ljp.hellogithub.activity.threadpool;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocalIntentService extends IntentService {
    private static final String TAG = "LocalIntentService";

    public LocalIntentService() {
        super(TAG);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand : flags:"+flags+ "--startId:"+  startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.e(TAG, "receive task :" +  action);
        SystemClock.sleep(3000);
        Log.e(TAG,"threadName:"+Thread.currentThread().getName());
        if ("com.ryg.action.TASK1".equals(action)) {
            Log.e(TAG, "handle task: " + action);
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "service destroyed.");
        super.onDestroy();
    }
}
