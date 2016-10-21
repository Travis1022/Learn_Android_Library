package com.download.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.download.R;
import com.download.controller.MainActivity;
import com.sunnybear.library.basic.ViewBinder;
import com.sunnybear.library.network.callback.DownloadCallback;
import com.sunnybear.library.network.progress.UIProgressResponseListener;
import com.sunnybear.library.util.MathUtils;
import com.sunnybear.library.util.SDCardUtils;

import java.io.File;

import butterknife.OnClick;

/**
 * Created by xuanwei.tian on 2016/10/20.
 */
public class MainViewBinder extends ViewBinder<MainActivity> implements View.OnClickListener {


    public MainViewBinder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreatedFinish() {

    }

    @OnClick(R.id.btn_download)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                /*// 内网下载问卷信息Zip文件
                mDispatch.download("http://10.103.18.196:8080/SFAInterface/appservice/downloadFile.htm?userLoginNumber=test100", SDCardUtils.getSDCardPath() + File.separator + "test100", new UIProgressResponseListener() {
                    @Override
                    public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                        String percent = MathUtils.percent((double) bytesRead, (double) contentLength, 0);
                        Log.e("Nielsen_test100", "bytesRead:" + bytesRead + "------------------contentLength:" + contentLength + "-------------------done" + done);
                        Log.e("Nielsen_test100", "percent:" + percent + "-------------------done" + done);

                    }
                }, new DownloadCallback() {
                    @Override
                    public void onDownloadSuccess(String url, File file) {

                    }
                }, true);*/

                //外网下载360免费WiFi.exe
                mDispatch.download("http://down.360safe.com/360ap/360freeap_beta_setup_freewifi.exe"
                        , SDCardUtils.getSDCardPath() + File.separator + "360freeap_beta_setup_freewifi.exe"
                        , new UIProgressResponseListener() {
                            @Override
                            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                                String percent = MathUtils.percent((double) bytesRead, (double) contentLength, 0);
                                Log.e("Nielsen_360", "bytesRead:" + bytesRead + "-------contentLength:" + contentLength + "-------------done:" + done);
                                Log.e("Nielsen_360", "progress:" + percent + "%--------done:" + done);
                            }
                        }, new DownloadCallback() {
                            @Override
                            public void onDownloadSuccess(String url, File file) {

                            }
                        }, true);

                break;
        }
    }
}
