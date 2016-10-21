package com.sunnybear.library.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 计时任务广播接收器
 * Created by chenkai.gu on 2016/10/12.
 */
public abstract class ReckonBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_TIMEOUT = "com.sunnybear.library.basic.ACTION_TIMEOUT";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_TIMEOUT:
                timeout(context);
                break;
        }
    }

    /**
     * 任务超时后的处理
     */
    public abstract void timeout(Context context);
}
