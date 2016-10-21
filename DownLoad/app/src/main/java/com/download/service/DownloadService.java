package com.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sunnybear.library.network.OkHttpRequestHelper;
import com.sunnybear.library.network.callback.DownloadCallback;
import com.sunnybear.library.network.progress.UIProgressResponseListener;
import com.sunnybear.library.util.Logger;

/**
 * 下载文件使用用的Service
 * Created by chenkai.gu on 2016/10/20.
 */
public class DownloadService extends Service {
    private DownloadBinder mDownloadBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadBinder = new DownloadBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("DownloadService已绑定");
        return mDownloadBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d("DownloadService绑定解除");
        stopSelf();
        return super.onUnbind(intent);
    }

    /**
     * 下载文件,带有进度处理
     *
     * @param url      下载地址
     * @param filePath 保存文件的路径
     * @param listener 下载进度回调
     * @param callback 下载文件回调
     * @param isCover  是否覆盖
     */
    public void download(String url, String filePath, UIProgressResponseListener listener, DownloadCallback callback, boolean isCover) {
        OkHttpRequestHelper.newInstance().download(url, filePath, listener, callback, isCover);
    }

    /**
     * DownloadService绑定类
     * Created by chenkai.gu on 2016/10/20.
     */
    public class DownloadBinder extends Binder {

        public DownloadService getService() {
            return DownloadService.this;
        }
    }
}
