package com.rjsoft.resourcelib;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

import static com.rjsoft.resourcelib.HostUrl.HOST_URL;


/**
 * Created by moligy on 2017/10/30.
 */

public class Constants {
    /**
     * 二维码扫描 action 类型
     * <p>
     * public static $QR_CODE_TEMPLATE = "http://###HTTP_HOST###/App/WebQrCode/QrCode?uid=###UID###&action=###ACTION###&code=###CODE###";
     */
    public interface ScanAction {
        String ACTION_SCORE_GIFT = "score_gift";//领积分
        String ACTION_ORDER_ADD = "order_add";//订单扫码添加
        String ACTION_ORDER_PAY = "order_pay";//订单扫码支付
        String ACTION_GOODS = "goods";//商品详情,goods_id|sku_id
        String ACTION_SUPPLIER = "supplier";//供应商,supplier_id
        String ACTION_SHOP = "shop";//店铺,shop_id
    }

    /**
     * 分享配置
     */
    public interface ShareConfig {
        /*** 分享的类型 */
         int SHARE_WEIXIN = 0;
         int SHARE_FRIEND = 1;
         int SHARE_QQ = 2;
         int SHARE_QQZONE = 3;
         int SHARE_SINA = 4;

        //        //==========友盟配置=============//
        //        // key
        //        let UMAppKey = "597adc04f5ade4020700132e"
        //        // channelid
        //        let UMChannelId = isDebug ? "PGY" : "App Store"
        //        // 微信的appKey和appSecret
        //        let UMWeChatAppKey = "wxf4c9cf709f733543"
        //        let UMWeChatAppSecret = "3ad3eb29d5aef37b11a44e05eed27e3c"
        //        // QQ的appKey和appSecret
        //        let UMQQAppKey = "1106222240"
        //        let UMQQAppSecret = "QJ62TqDLvOSz61PM"
        //        // 新浪的appKey和appSecret
        //        let UMSinaAppKey = "73421457"
        //        let UMSinaAppSecret = "a422be20542c594e06538f5db5b90abc"
         String WECHAT_SHARE_KEY = "wxf4c9cf709f733543";
         String WECHAT_SHARE_SECRET = "3ad3eb29d5aef37b11a44e05eed27e3c";
         String QQ_SHARE_KEY = "1106222240";
         String QQ_SHARE_SECRET = "QJ62TqDLvOSz61PM";
         String SINA_SHARE_KEY = "73421457";
         String SINA_SHARE_SECRET = "a422be20542c594e06538f5db5b90abc";
    }

    //setting
    public static final int PAGE_COUNT = 10;//每页请求数目
    public static final boolean IS_DEBUG = BuildConfig.DEBUG; //是否调试版本



    //sharePreference
    public static final String GOODS_HISTORY_SEARCH = "goods_history_search"; //历史搜索
    public static final String COURSE_HISTORY_SEARCH = "course_history_search"; //历史搜索
    public static final String IMAGE_PATH = "images"; //图片缓存目录外部存储
    //location
    public static final String SELECT_CITY = "select_city"; //手动选择城市 优先取这个
    public static final String LOCATION_CITY = "location_city"; //定位城市
    public static final String LOCATION_CITY_DISTRICT = "location_city_district"; //定位城市区
    public static final String MD5_KEY = "d367f4699214cec412f7c2a1d513fe05";


    public static String getImageURL(String url) {
        return HOST_URL + url;
    }

    public static String getDownloadPath(String fileName) {
        return FileDownloadUtils.getDefaultSaveRootPath()
                + File.separator + "download" + File.separator
                + fileName; //下载保存路径
    }

}
