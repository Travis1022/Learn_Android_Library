package com.download.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.download.service.DownloadService;
import com.download.view.MainViewBinder;
import com.sunnybear.library.basic.DispatchActivity;
import com.sunnybear.library.network.callback.DownloadCallback;
import com.sunnybear.library.network.progress.UIProgressResponseListener;
import com.sunnybear.library.util.Logger;

/**
 * 下载文件
 */
public class MainActivity extends DispatchActivity<MainViewBinder> {
    private DownloadService mDownloadService;
    private ServiceConnection mServiceConnection;

    @Override
    protected MainViewBinder getViewBinder(Context context) {
        return new MainViewBinder(context);
    }

    @Override
    protected void dispatchModelOnCreate(@Nullable Bundle savedInstanceState) {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Logger.d("已经绑定Service");
                mDownloadService = ((DownloadService.DownloadBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Logger.d("断开Service绑定");
            }
        };
    }

    @Override
    protected void dispatchModelOnStart() {
        bindService(DownloadService.class, mServiceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }


    public void download(String url, String filePath, UIProgressResponseListener listener, DownloadCallback callback, boolean isCover) {
        mDownloadService.download(url, filePath, listener, callback, isCover);
    }
}
