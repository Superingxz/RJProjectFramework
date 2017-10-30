package com.rjsoft.resourcelib.listener;

/**
 * Desc
 * Author feng
 * Date   2017/9/6.
 */

public interface WXPayListener {
    void onPaySuccess();

    void onPayCancel();

    void onPayError(String message);

    public static class WXPayListenerManage {
        private static WXPayListener mWXPayListener;

        public static void setmWXPayListener(WXPayListener listener) {
            mWXPayListener = listener;
        }

        public static WXPayListener getmWXPayListener() {
            return mWXPayListener;
        }
    }
}
