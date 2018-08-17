package com.ljp.hellogithub.activity.threadpool;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.threadpool.http.DownloadCallback;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.net.RestClient;
import com.ljp.hellogithub.net.RestCreate;
import com.ljp.hellogithub.net.callback.IError;
import com.ljp.hellogithub.net.callback.IProgress;
import com.ljp.hellogithub.net.callback.ISuccess;
import com.ljp.hellogithub.util.DialogUtil;
import com.ljp.hellogithub.util.FileUtils;
import com.ljp.hellogithub.util.Logger;
import com.ljp.hellogithub.util.permission.KbPermission;
import com.ljp.hellogithub.util.permission.KbPermissionListener;
import com.ljp.hellogithub.util.permission.KbPermissionUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("unchecked")
public class ThreadPoolExecutorActivity extends BaseActivity {

    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.progress2)
    ProgressBar mProgress2;
    @BindView(R.id.btn2)
    Button mBtn2;


    private int count;
    private String downloadUrl = "http://terminal.sqkx.net/upload/hzfx_1.5.6_android.apk";
    public File mFile;

    //系统下载
    private static android.app.DownloadManager manager; // 系统下载类
    static DownloadCompeleteReceiver receiver; // 广播
    static long downloadId = 0;

    private static final String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/DownloadFile";
    private String mDownloadPath; //下载到本地的路径

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);
        ButterKnife.bind(this);

        manager = (android.app.DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompeleteReceiver();
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
    @OnClick({R.id.btn, R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn://系统下载
                String name = downloadUrl;
                if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO)) {
                    int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
                    if (i != -1) {
                        name = name.substring(i);
                        Log.e("DownloadHandler", name);
                        mDownloadPath = PATH_CHALLENGE_VIDEO + name;
                    }
                }

                if (TextUtils.isEmpty(mDownloadPath)) {
                    Log.e("DownloadHandler", "downloadVideo: 存储路径为空了");
                    return;
                }
                //建立一个文件
                mFile = new File(mDownloadPath);

                Uri uri = Uri.parse(downloadUrl);
                android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI
                        | android.app.DownloadManager.Request.NETWORK_MOBILE);

                request.setTitle("HelloGithub");
                request.setDestinationInExternalFilesDir(this, downloadUrl, name);
                request.setDescription("版本更新...");
                // 显示进度
                request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE);
                downloadId = manager.enqueue(request);

                IntentFilter filter = new IntentFilter(
                        android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                registerReceiver(receiver, filter);
                break;
            case R.id.btn1://多线程下载
                threadPoolExecuteDownload();
                break;
            case R.id.btn2://单线程下载
                if (KbPermissionUtils.needRequestPermission()) { //判断是否需要动态申请权限
                    KbPermission.with(this)
                            .requestCode(100)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE) //需要申请的权限(支持不定长参数)
                            .callBack(new KbPermissionListener() {
                                @Override
                                public void onPermit(int requestCode, String... permission) { //允许权限的回调
                                    threadDownload(); //处理具体下载过程
                                }

                                @Override
                                public void onCancel(int requestCode, String... permission) { //拒绝权限的回调
                                    KbPermissionUtils.goSetting(ThreadPoolExecutorActivity.this); //跳转至当前app的权限设置界面
                                }
                            })
                            .send();
                } else {
                    threadDownload();
                }

                break;
        }
    }

    /**
     * 使用retrofit2.0单线程下载
     */
    private void threadDownload() {
        RestClient.builder()
                .url(downloadUrl)
                .mClass(File.class)
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.e("onError",msg);
                    }
                })
                .success(new ISuccess<File>() {
                    @Override
                    public void onSuccess(final File file) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                boolean haveInstallPermission;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    //先获取是否有安装未知来源应用的权限
                                    haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                                    if (!haveInstallPermission) {//没有权限
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ThreadPoolExecutorActivity.this);
                                        builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限");
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    startInstallPermissionSettingActivity();
                                                }
                                            }
                                        });
                                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        builder.create().show();
                                        return;
                                    }
                                }
                                installApk(file);
                            }
                        });

                    }
                })
                .progress(new IProgress() {
                    @Override
                    public void progress(int progress) {
                        mProgress2.setProgress(progress);
                    }
                })
                .build()
                .download();
    }

    /**
     * okhttp3.0多线程下载
     */
    private void threadPoolExecuteDownload() {
        DownloadManager.getInstance()
                .download(downloadUrl,
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

                        boolean haveInstallPermission;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //先获取是否有安装未知来源应用的权限
                            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                            if (!haveInstallPermission) {//没有权限
                                AlertDialog.Builder builder = new AlertDialog.Builder(ThreadPoolExecutorActivity.this);
                                builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            startInstallPermissionSettingActivity();
                                        }
                                    }
                                });
                                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                                return;
                            }
                        }
                        installApk(file);
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
    }

    /**
     * 调用系统下载的广播
     *
     * @author lijipei
     */
    public class DownloadCompeleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.e("getAction", "---->" + intent.getAction());
            if (intent.getAction().equals(
                    android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                if (downloadId == intent.getLongExtra(
                        android.app.DownloadManager.EXTRA_DOWNLOAD_ID, -1)) {
                        File file = new File(mDownloadPath);
                        Intent i = new Intent();
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        i.setAction(Intent.ACTION_VIEW);
                        i.setDataAndType(FileUtils.getUriForFile(context, file),
                                "application/vnd.android.package-archive");
                        context.startActivity(i);
                    //Toast.makeText(context, "下载完毕!", Toast.LENGTH_SHORT).show();
                }
            }
            context.unregisterReceiver(receiver);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10086 &&  resultCode == RESULT_OK) {
            installApk(mFile);
        }
    }

    public void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(FileUtils.getUriForFile(this, file)
                , "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * android艺术探索学习
     */
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
