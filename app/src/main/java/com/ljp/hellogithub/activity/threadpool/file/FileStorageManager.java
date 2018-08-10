package com.ljp.hellogithub.activity.threadpool.file;

import android.content.Context;
import android.os.Environment;

import com.ljp.hellogithub.util.Md5Utills;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/18.
 */

public class FileStorageManager {

    private static final FileStorageManager sManager = new FileStorageManager();
    private Context mContext;

    public static FileStorageManager getInstance(){
        return sManager;
    }

    public FileStorageManager() {
    }

    public void init(Context context){
        this.mContext = context;
    }

    public File getFileByName(String url){
        File parent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //外部存储卡位置
            parent = mContext.getExternalCacheDir();
        }else{
            parent = mContext.getCacheDir();
        }

        String fileName = Md5Utills.generateCode(url);

        File file = new File(parent,fileName+".apk");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
