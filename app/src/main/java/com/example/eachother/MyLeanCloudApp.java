package com.example.eachother;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by 袁刚 on 2017/5/11.
 */

public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"CXH7HAHTHOH2gdUcgkHKqed6-gzGzoHsz","uSUHRmdRwBQsYDwCeNYfL3FC");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }
}
