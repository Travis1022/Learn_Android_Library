package com.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sunnybear.library.util.Logger;

/**
 * 上传文件使用的Service
 * Created by chenkai.gu on 2016/10/20.
 */
public class UploadService extends Service {
    private UploadBinder mUploadBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mUploadBinder = new UploadBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("UploadService已绑定");
        return mUploadBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d("UploadService绑定解除");
        stopSelf();
        return super.onUnbind(intent);
    }

    /**
     * UploadService绑定类
     * Created by chenkai.gu on 2016/10/20.
     */
    public class UploadBinder extends Binder {

        public UploadService getService() {
            return UploadService.this;
        }
    }
}
