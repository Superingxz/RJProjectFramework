package com.rjsoft.resourcelib.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.rjsoft.resourcelib.listener.WXPayListener;
import com.softgarden.baselibrary.BaseApplication;
import com.softgarden.baselibrary.utils.L;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.trello.rxlifecycle2.components.RxActivity;
import static com.rjsoft.resourcelib.Constants.ShareConfig.WECHAT_SHARE_KEY;

/**
 * Created by DELL on 2017/8/17.
 */

public class WXPayEntryActivity extends RxActivity implements IWXAPIEventHandler {
    IWXAPI WXAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXAPI = WXAPIFactory.createWXAPI(BaseApplication.getInstance(), WECHAT_SHARE_KEY);
        WXAPI.registerApp(WECHAT_SHARE_KEY);
        WXAPI.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WXAPI.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        L.i("onPayFinish, errCode = " + resp.errCode + "\n" + resp.getType() + "\n" + resp.errStr + "\n" + resp.transaction);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WXPayListener wxPayListener = WXPayListener.WXPayListenerManage.getmWXPayListener();
            if (resp.errCode == 0) {//支付成功
                if (wxPayListener != null) {
                    wxPayListener.onPaySuccess();
                }
                finish();
            } else {//取消或者失败
                //errCode -2 为取消
                if (resp.errCode == -2) {
                    if (wxPayListener != null) {
                        wxPayListener.onPayCancel();
                    }
                } else {
                    if (wxPayListener != null) {
                        wxPayListener.onPayError(resp.errStr);
                    }
                }
                finish();
            }

        }

    }
}
