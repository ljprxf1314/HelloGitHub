package com.ljp.hellogithub.activity.threadpool;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.threadpool.http.DownloadCallback;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.util.DialogUtil;
import com.ljp.hellogithub.util.FileUtils;
import com.ljp.hellogithub.util.Logger;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolExecutorActivity extends BaseActivity {

    @BindView(R.id.progress)
    ProgressBar mProgress;
    private int count;
    private String downloadUrl = "http://terminal.sqkx.net/upload/hzfx_1.5.6_android.apk";

    private File mFile;

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
        DownloadManager.Holder.getInstance();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.btn, R.id.btn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                //                runThreadPool();

                boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {
                    DialogUtil.showCustomDialog(ThreadPoolExecutorActivity.this, "安装权限", "需要打开允许来自此来源，请去设置中开启此权限",
                            "确定", "取消", new DialogUtil.MyCustomDialogListener2() {
                                @Override
                                public void ok() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        //此方法需要API>=26才能使用
                                        toInstallPermissionSettingIntent();
                                    }
                                }

                                @Override
                                public void no() {

                                }
                            });
                    return;
                }
                break;
            case R.id.btn1:
                DownloadManager.getInstance().download(downloadUrl,
                        new DownloadCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void success(File file) {
                                Log.e("success", "走了一次");
                                if (count < 1) {
                                    count++;
                                    return;
                                }
                                mFile = file;
                                //先判断是否有安装未知来源应用的权限
                                /*boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                                if(!haveInstallPermission){
                                    DialogUtil.showCustomDialog(ThreadPoolExecutorActivity.this, "安装权限", "需要打开允许来自此来源，请去设置中开启此权限",
                                            "确定", "取消", new DialogUtil.MyCustomDialogListener2() {
                                                @Override
                                                public void ok() {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        //此方法需要API>=26才能使用
                                                        toInstallPermissionSettingIntent();
                                                    }
                                                }

                                                @Override
                                                public void no() {

                                                }
                                            });
                                    return;
                                }*/
                                installApk(file);

                                //                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                //                        runOnUiThread(new Runnable() {
                                //                            @Override
                                //                            public void run() {
                                //                                mImageView.setImageBitmap(bitmap);
                                //                            }
                                //                        });
                                Logger.debug("nate", "success " + file.getAbsoluteFile());
                            }

                            @Override
                            public void fail(int errorCode, String errorMessage) {
                                Logger.debug("nate", "fail " + errorCode + "  " + errorMessage);
                            }

                            @Override
                            public void progress(int progress) {
                                Logger.debug("nate", "progress    " + progress);
                                mProgress.setProgress(progress);
                            }
                        });
                break;
        }
    }


    private void installApk(File file) {
        //        File file1 = new File("file://" + file.getAbsoluteFile().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(FileUtils.getUriForFile(this, file)
                , "application/vnd.android.package-archive");
        this.startActivity(intent);
    }

    /**
     * 开启安装未知来源权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            installApk(mFile);
        }
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
