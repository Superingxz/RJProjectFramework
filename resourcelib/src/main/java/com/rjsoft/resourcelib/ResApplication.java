package com.rjsoft.resourcelib;

import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.pgyersdk.crash.PgyCrashManager;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.softgarden.baselibrary.BaseApplication;
import com.softgarden.baselibrary.widget.RefreshDelegateLayout;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.net.Proxy;

import static com.rjsoft.resourcelib.Constants.IS_DEBUG;
import static com.rjsoft.resourcelib.Constants.ShareConfig.QQ_SHARE_KEY;
import static com.rjsoft.resourcelib.Constants.ShareConfig.QQ_SHARE_SECRET;
import static com.rjsoft.resourcelib.Constants.ShareConfig.SINA_SHARE_KEY;
import static com.rjsoft.resourcelib.Constants.ShareConfig.SINA_SHARE_SECRET;
import static com.rjsoft.resourcelib.Constants.ShareConfig.WECHAT_SHARE_KEY;
import static com.rjsoft.resourcelib.Constants.ShareConfig.WECHAT_SHARE_SECRET;

/**
 * Created by moligy on 2017/10/30.
 */

public class ResApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);

        //友盟初始化
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin(WECHAT_SHARE_KEY, WECHAT_SHARE_SECRET);
        PlatformConfig.setQQZone(QQ_SHARE_KEY, QQ_SHARE_SECRET);
        PlatformConfig.setSinaWeibo(SINA_SHARE_KEY, SINA_SHARE_SECRET, "");
        //每次授权登录都要跳转到授权页面
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        //config.isOpenShareEditActivity(true);
        UMShareAPI.get(this).setShareConfig(config);

        //蒲公英初始化
        PgyCrashManager.register(this);

        //初始化文件下载器
        initFileDownloader();
    }

    private void initFileDownloader() {
        // just for open the log in this demo project.
        FileDownloadLog.NEED_LOG = IS_DEBUG;

        /**
         * just for cache Application's Context, and ':filedownloader' progress will NOT be launched
         * by below code, so please do not worry about performance.
         * @see FileDownloader#init(Context)
         */
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                ))
                .commit();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        RefreshDelegateLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        RefreshDelegateLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
        });

    }
}
