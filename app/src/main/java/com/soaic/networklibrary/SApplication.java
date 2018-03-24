package com.soaic.networklibrary;

import android.app.Application;
import android.content.Context;

/**
 * Created by sxiao on 2018/3/24.
 */

public class SApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
