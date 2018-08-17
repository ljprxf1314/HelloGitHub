package com.ljp.hellogithub.activity.threadpool;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/8/8
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MyAsyncTask extends AsyncTask<ProgressBar,Integer,Boolean> {//<Params(参数), Progress(进度), Result(结果)>

    private final String TAG = "MyAsyncTask";

    ProgressBar pb;
    int progress  = 0;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //在主线程中执行 --可做一些准备工作
        Log.e(TAG,"onPreExecute:开始");
    }

    @Override
    protected Boolean doInBackground(ProgressBar... progressBars) {
        //在线程池中执行此方法用于执行耗时任务
        //此方法可以通过publicProgress方法来更新任务的进度,publicPressage会调用onPressageUpdate方法
        //此方法需要返回计算结果给onPostExecute
        pb = progressBars[0];
        while(true){
            publishProgress(progress);

            if (progress != 100) {
                progress += 10;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return true;
            }
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //在主线程中执行,执行进度改变时会调用此方法刷新
        Log.e(TAG,"onProgressUpdate--"+values[0]);
        super.onProgressUpdate(values);
        pb.setProgress(progress);

    }

    @Override
    protected void onPostExecute(Boolean bo) {
        super.onPostExecute(bo);
        //在主线程中执行,异步任务执行后此方法会被调用
        if (bo){
            Log.e(TAG,"成功");
        }
        Log.e(TAG,"onPostExecute");
    }

    @Override
    protected void onCancelled() {
        Log.e(TAG,"onCancelled:任务取消");
        super.onCancelled();
        //当异步任务被取消时,此方法会被调用,这个时候onPostExecute不会被调用
    }
}
