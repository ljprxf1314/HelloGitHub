package com.ljp.hellogithub.activity.threadpool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.threadpool.http.DownloadCallback;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.util.Logger;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolExecutorActivity extends BaseActivity {

    private int count;
    private String downloadUrl = "http://terminal.sqkx.net/upload/hzfx_1.5.6_android.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);
        ButterKnife.bind(this);

        /* HttpManager.getInstance().
                asyncRequest("http://szimg.mukewang.com/5763765d0001352105400300-360-202.jpg", new DownloadCallback() {
                    @Override
                    public void success(File file) {
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImageView.setImageBitmap(bitmap);
                            }
                        });
                        Logger.debug("nate", "success " + file.getAbsoluteFile());
                    }

                    @Override
                    public void fail(int errorCode, String errorMessage) {
                        Logger.debug("nate", "fail " + errorCode + "  " + errorMessage);
                    }

                    @Override
                    public void Progress(int progress) {
                    }
                });*/

    }


    @OnClick({R.id.btn, R.id.btn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                runThreadPool();
                break;
            case R.id.btn1:
                DownloadManager.getInstance().download(downloadUrl,
                        new DownloadCallback() {
                            @Override
                            public void success(File file) {
                                if (count < 1) {
                                    count++;
                                    return;
                                }
                                installApk(file);
                                //                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                //                        runOnUiThread(new Runnable() {
                                //                            @Override
                                //                            public void run() {
                                //                                mImageView.setImageBitmap(bitmap);
                                //                            }
                                //                        });
                                Logger.debug("nate", "success " + file.getAbsoluteFile());            }

                            @Override
                            public void fail(int errorCode, String errorMessage) {
                                Logger.debug("nate", "fail " + errorCode + "  " + errorMessage);
                            }

                            @Override
                            public void Progress(int progress) {
                                Logger.debug("nate", "progress    " + progress);
                                //                mProgress.setProgress(progress);
                            }
                        });
                break;
        }
    }


    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        this.startActivity(intent);
    }

    private void runThreadPool() {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(command);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(command);

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        // 2000ms后执行command
        scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
        // 延迟10ms后，每隔1000ms执行一次command
        scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(command);
    }
}
