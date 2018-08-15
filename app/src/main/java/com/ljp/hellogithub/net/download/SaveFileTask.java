package com.ljp.hellogithub.net.download;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ljp.hellogithub.net.callback.IRequest;
import com.ljp.hellogithub.net.callback.ISuccess;
import com.ljp.hellogithub.util.FileUtil;
import com.ljp.hellogithub.util.FileUtils;
import com.ljp.hellogithub.util.UIUtils;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 傅令杰 on 2017/4/2
 */

final class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private Context mContext;

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS, Context mContext) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.mContext = mContext;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(FileUtils.getUriForFile(mContext,file), "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(install);
        }
    }
}
