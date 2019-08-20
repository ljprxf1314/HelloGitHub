package com.ljp.hellogithub.activity.io;

import android.util.Log;

import com.ljp.hellogithub.activity.io.callback.DownloadCallback;
import com.ljp.hellogithub.activity.threadpool.http.HttpManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;


/**
 * Created by lijipei on 2019/8/20.
 */

public class DownloadImgRunnable implements Runnable {

    private String TAG = "DownloadImgRunnable";

    private long start,end;
    private File file;
    private String url;
    private DownloadCallback callback;

    public DownloadImgRunnable(long start, long end, File file, String url,DownloadCallback callback) {
        this.start = start;
        this.end = end;
        this.file = file;
        this.url = url;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
            randomAccessFile.seek(start);
            byte[] buffer = new byte[1024];
            int progress = 0;

            Log.e(TAG,"开始位置:"+start+"--结束位置:"+end);
            //同步请求参数
            Response response = HttpManager.getInstance().syncRequestByRange(url, start, end);
            //获取下载流
            InputStream inputStream = response.body().byteStream();
            int len = 0;
            while ((len = inputStream.read(buffer))!=-1){
                randomAccessFile.write(buffer,0,len);
                progress+=len;
                Log.e(TAG,"下载进度:"+progress);
            }
            randomAccessFile.close();
            inputStream.close();
            callback.success();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.fault();
        } catch (IOException e) {
            e.printStackTrace();
            callback.fault();
        }
    }
}
