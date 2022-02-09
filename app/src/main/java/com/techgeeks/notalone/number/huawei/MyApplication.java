package com.techgeeks.notalone.number.huawei;

import android.app.Application;

import com.huawei.hms.ads.HwAds;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HwAds.init(this);
    }
}
