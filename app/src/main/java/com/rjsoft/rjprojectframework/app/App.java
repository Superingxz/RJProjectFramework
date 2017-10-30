package com.rjsoft.rjprojectframework.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.rjsoft.resourcelib.ResApplication;
import com.rjsoft.resourcelib.utils.PushUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Desc
 * Author moligy
 * Date   2017/6/20.
 */

public class App extends ResApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        PushUtil.setOnPushListener(new PushUtil.OnPushListener() {
            @Override
            public void onSetAlias(String alias) {
                JPushInterface.setAlias(App.getInstance(), 0, alias); //极光推送
                JPushInterface.resumePush(App.getInstance());
            }

            @Override
            public void onStop() {
                JPushInterface.setAlias(App.getInstance(), 0, "");  //取消极光推送
                JPushInterface.stopPush(App.getInstance());
            }
        });

    }
}
