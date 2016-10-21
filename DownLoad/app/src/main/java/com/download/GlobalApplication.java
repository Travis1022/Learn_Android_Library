package com.download;

import android.app.Application;

import com.sunnybear.library.network.NetworkConfiguration;
import com.sunnybear.library.util.SDCardUtils;

import java.io.File;

/**
 * Created by xuanwei.tian on 2016/10/20.
 */
public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkConfiguration.initialize(getApplicationContext(), 15 * 1000, 20 * 1000, 20 * 1000, ""
                , SDCardUtils.getSDCardPath() + File.separator + getProjectName()
                , 50 * 1024 * 1024);
    }

    private String getProjectName() {
        return "Download";
    }
}
