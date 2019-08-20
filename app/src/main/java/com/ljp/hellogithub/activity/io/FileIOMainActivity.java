package com.ljp.hellogithub.activity.io;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.activity.io.callback.DownloadCallback;
import com.ljp.hellogithub.activity.threadpool.http.HttpManager;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.net.RestCreate;
import com.ljp.hellogithub.util.permission.KbPermission;
import com.ljp.hellogithub.util.permission.KbPermissionListener;
import com.ljp.hellogithub.util.permission.KbPermissionUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lijipei on 2019/8/14.
 */

public class FileIOMainActivity extends BaseActivity implements DownloadCallback{

    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.btn3)
    Button mBtn3;
    @BindView(R.id.btn4)
    Button mBtn4;
    @BindView(R.id.btn5)
    Button mBtn5;
    @BindView(R.id.btn6)
    Button mBtn6;

    private String TAG = "FileIOMainActivity";

    private String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566809125&" +
            "di=74e028b0f5bd9c69a42d0e429351e209&imgtype=jpg&er=1&src=http%3A%2F%2Fpic39.nipic." +
            "com%2F20140327%2F17556992_134152074389_2.jpg";//"http://imgtest.sqkx.net/app/qmhs_merchant_1.1.41_anzhi.apk";
    //图片存储路径
    private static final String picPath = Environment
            .getExternalStorageDirectory() + "/downloadFile/img/";
    private String downloadUrl;
    //文件总长度
    private long contentLength;
    //文本文件存储路径
    private static final String strPath = Environment
            .getExternalStorageDirectory() + "/downloadFile/text/";

    private int count = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io);
        ButterKnife.bind(this);

//        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return null;
//            }
//        });
//        futureTask.get();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3,R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                mProgress.setProgress(0);

                if (KbPermissionUtils.needRequestPermission()) { //判断是否需要动态申请权限
                    KbPermission.with(this)
                            .requestCode(100)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE) //需要申请的权限(支持不定长参数)
                            .callBack(new KbPermissionListener() {
                                @Override
                                public void onPermit(int requestCode, String... permission) { //允许权限的回调
                                    inputStreamDownload();
                                }

                                @Override
                                public void onCancel(int requestCode, String... permission) { //拒绝权限的回调
                                    KbPermissionUtils.goSetting(FileIOMainActivity.this); //跳转至当前app的权限设置界面
                                }
                            })
                            .send();
                } else {
                    inputStreamDownload();
                }

                break;
            case R.id.btn2:
                writeTxt("天行健君子以自强不息!");
                break;
            case R.id.btn3:
                readTxt();
                break;
            case R.id.btn4://字节流转字符流读取文件
                readInputToReader();
                break;
            case R.id.btn5:
                readerText();
                break;
            case R.id.btn6:
                randomAccessDownload();
                break;
        }
    }

    /**
     * 使用文件字节流下载文件
     */
    private void inputStreamDownload() {
        RestCreate.getRestService()
                .download(URL, new WeakHashMap<String, Object>())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                        if (response.isSuccessful()) {//请求成功
                            //下载文件放在子线程
                            Thread mThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    //保存到本地
                                    String name = System.currentTimeMillis() + URL.substring(URL.lastIndexOf("."));
                                    downloadUrl = picPath + name;

                                    File file = new File(createDir(picPath), name);
                                    Log.e(TAG, "下载路径:" + file.toString());
                                    writeFileToDisk(response, file);
                                }
                            };
                            mThread.start();
                        } else {
                            showToast("请求失败");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        showToast("请求异常:" + t.toString());
                        Log.e(TAG, t.toString());
                    }
                });
    }

    private void writeFileToDisk(Response<ResponseBody> response, final File file) {
        long totalLenght = response.body().contentLength();//文件总长度
        Log.e(TAG, "总长度: " + totalLenght);
        long currentLength = 0;//累计进度

        InputStream is = response.body().byteStream();
        OutputStream os = null;
        byte[] bytes = new byte[1024];
        int len = 0;
        try {

            os = new FileOutputStream(file);

            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                currentLength += len;
                Log.e("DownloadHandler", "当前进度: " + currentLength);
                int progress = (int) (currentLength / totalLenght * 100);
                mProgress.setProgress(progress);
                if (progress == 100) {//下载成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                            mIvPic.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用字节流向文件中写入文字
     */
    private void writeTxt(String str) {
        File file = new File(createDir(strPath), "aaa.txt");
        //1.判断文件是否存在,不存在就创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                showToast("创建文件错误");
                return;
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            //创建字符串输入流
            is = new ByteArrayInputStream(str.getBytes("UTF-8"));
            //创建文件输出流
            os = new FileOutputStream(file);
            //写入文件中
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            showToast("写入成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字节流读取文字
     */
    private void readTxt() {
        File file = new File(strPath + "aaa.txt");
        if (!file.exists()) {
            showToast("文件不存在,请先创建文件");
            return;
        }
        InputStream is = null;
        try {
            //获取文件输入流
            is = new FileInputStream(file);
            StringBuffer sb = new StringBuffer();
            //1.单个字节读取,容易出现文字读取不全问题,且效率低下
            //            int temp;
            //            while ((temp = is.read()) != -1) {
            //                sb.append((char) temp);
            //            }
            //2.通过数组读取效率高,字符串的话不容易出现乱码
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                String string = new String(bytes, 0, len);
                sb.append(string);
            }

            mTvText.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null)
                is.close();
            }catch (IOException e){
            }
        }
    }

    /**
     * 字节流转字符流读取文件
     */
    private void readInputToReader(){
        File file = new File(strPath + "aaa.txt");
        if (!file.exists()) {
            showToast("文件不存在,请先创建文件");
            return;
        }
        InputStream is = null;
        BufferedReader br = null;
        try {
            //获取文件输入流
            is = new FileInputStream(file);
            //转换成字符流
            InputStreamReader inputStreamReader =  new InputStreamReader(is);
            //使用缓冲字符流
            br = new BufferedReader(inputStreamReader);

            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }

            mTvText.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用文件字符流读取文字
     */
    private void readerText(){
        File file = new File(strPath + "aaa.txt");
        if (!file.exists()) {
            showToast("文件不存在,请先创建文件");
            return;
        }
        BufferedReader bufferedReader = null;
        try {
            //文件读取流
            FileReader fileReader = new FileReader(file);
            //使用缓冲字符流
            bufferedReader = new BufferedReader(fileReader);

            StringBuffer sb = new StringBuffer();
            //1.读取一行
//            String str;
//            while ((str = bufferedReader.readLine()) != null) {
//                sb.append(str);
//            }
            //2,
            int temp;
            while ((temp = bufferedReader.read()) != -1) {
                sb.append((char) temp);
            }
            mTvText.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 多线程下载
     */
    private void randomAccessDownload(){
        final ExecutorService executorService = Executors.newFixedThreadPool(count);

        //保存到本地
        String name = System.currentTimeMillis() + URL.substring(URL.lastIndexOf("."));
        downloadUrl = picPath + name;

        final File file = new File(createDir(picPath), name);
        Log.e(TAG, "下载路径:" + file.toString());

        HttpManager.getInstance().asyncRequest(URL, new okhttp3.Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()){
                    contentLength = response.body().contentLength();
                    Log.e(TAG, "总长度:"+contentLength);
                    long threadDownloadLenght = contentLength/count;
                    Log.e(TAG, "threadDownloadLenght:"+threadDownloadLenght);
                    for (int i=0;i<count;i++){
                        long startSize = i*threadDownloadLenght;
                        long endSize = 0;
                        if (i == count-1){
                            endSize = contentLength-1;
                        }else{
                            endSize = (i+1)*threadDownloadLenght-1;
                        }
                        Log.e(TAG, i+"--start:"+startSize+"--end:"+endSize);
                        executorService.execute(new DownloadImgRunnable(startSize,endSize,file,URL,
                                FileIOMainActivity.this));
                    }
                }
            }
        });
    }

    @Override
    public void success() {
        final File file = new File(downloadUrl);
        try {
            long lenght = file.length();
            Log.e(TAG, "文件长度->"+lenght);
            if (lenght==contentLength){
                Log.e(TAG, "下载完成");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                        mIvPic.setImageBitmap(bitmap);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fault() {

    }


    /**
     * 判断目录是否存在,不存在就创建
     *
     * @param sdcardDirName
     * @return
     */
    private static File createDir(String sdcardDirName) {
        final File fileDir = new File(sdcardDirName);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

}
