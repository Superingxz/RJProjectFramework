package com.softgarden.baselibrary.base.databinding;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.softgarden.baselibrary.BuildConfig;
import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.CommonToolbar;
import com.softgarden.baselibrary.widget.LoadingDialogManage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 * Created by MirkoWu on 2017/3/22 0022.
 */

public abstract class DataBindingActivity<B extends ViewDataBinding> extends RxAppCompatActivity implements IBaseDisplay {

    public final String TAG = getClass().getSimpleName();
    public static final int RESULT_LOGIN = 0x00001234;

    private boolean mNightMode;

    protected B binding;
    private CommonToolbar commonToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示当前的Activity路径
        if (BuildConfig.DEBUG) {
            L.e("当前打开的Activity:  " + getClass().getName());
        }

        initContentView();
        ARouter.getInstance().inject(this);//传递参数所需注解
        initPresenter();
        initialize();
        //        mNightMode = (boolean) SPUtils.get(MODE_NIGHT, false);
        //        if (mNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    protected void initPresenter() {
    }

    public void initContentView() {
        commonToolbar = setToolbar();
        if (commonToolbar != null) {
            //添加标题栏
            LinearLayout view = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            view.setOrientation(LinearLayout.VERTICAL);
            view.setFitsSystemWindows(true);
            view.addView(commonToolbar);
            view.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            view.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.toolbar_line));
            // View contentView = getLayoutInflater().inflate(getLayoutId(), view, false);
            binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), view, false);

            view.addView(binding.getRoot());
            setContentView(view);
            setSupportActionBar(commonToolbar);//这里才是真的将toolbar设置为actionbar
        } else {
            binding = DataBindingUtil.setContentView(this, getLayoutId());
        }
    }

    public CommonToolbar getToolbar() {
        return commonToolbar;
    }

    @Override
    public void useNightMode(boolean isNight) {
        //       boolean night = (boolean) SPUtils.get(MODE_NIGHT, false);
        //        if (night) {
        //            SPUtils.put(MODE_NIGHT, false);
        //            AppCompatDelegate.setDefaultNightMode(
        //                    AppCompatDelegate.MODE_NIGHT_NO);
        //        } else {
        //            SPUtils.put(MODE_NIGHT, true);
        //            AppCompatDelegate.setDefaultNightMode(
        //                    AppCompatDelegate.MODE_NIGHT_YES);
        //        }
        //        // recreate();
        //        reload();
    }


    @Override
    protected void onResume() {
        setPortrait();
        super.onResume();
        //        //检查日夜模式
        //        boolean night = (boolean) SPUtils.get(MODE_NIGHT, false);
        //        if (night != mNightMode) {
        //            if (night) {
        //                AppCompatDelegate.setDefaultNightMode(
        //                        AppCompatDelegate.MODE_NIGHT_YES);
        //            } else {
        //                AppCompatDelegate.setDefaultNightMode(
        //                        AppCompatDelegate.MODE_NIGHT_NO);
        //            }
        //            recreate();
        //            // reload();
        //        }
    }

    /**
     * 限制Activity横竖屏显示
     */
    protected void setPortrait() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void launchActivity(Class<? extends Activity> cls) {
        this.startActivity(new Intent(this, cls));
    }

    public Postcard getRouter(String url) {
        return ARouter.getInstance().build(url);
    }

    public void openActivity(String url) {
        ARouter.getInstance().build(url).navigation();
    }


    /**
     * 打开Activity 后finish自己，
     * 直接调用finish会改变转场动画，所以要在NavCallback中finish
     *
     * @param url
     */
    public void openActivityFinishSelf(String url) {
        ARouter.getInstance().build(url)
                .navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }
                });
    }

    public void openActivityForResult(String url, int requestCode) {
        ARouter.getInstance().build(url).navigation(this, requestCode);
    }

    public void openActivityForResult(String url, int requestCode, NavCallback navCallback) {
        ARouter.getInstance().build(url).navigation(this, requestCode, navCallback);
    }

    public Activity getActivity() {
        return this;
    }

//    private LoadingDialog mLoadingDialog;
//    private int showLoadingDialog = 0;
//
//    @Override
//    public synchronized void showProgressDialog() {
//        if (showLoadingDialog == 0) {
//            if (mLoadingDialog == null)
//                mLoadingDialog = new LoadingDialog(getActivity());
//            if (!this.isFinishing())
//                mLoadingDialog.show();
//        }
//        showLoadingDialog++;
//    }
//
//    @Override
//    public synchronized void hideProgressDialog() {
//        showLoadingDialog--;
//        if (mLoadingDialog != null && showLoadingDialog == 0) {
//            mLoadingDialog.dismiss();
//        }
//    }


    @Override
    public void showProgressDialog() {
        if(!this.isFinishing()) {
            LoadingDialogManage.showLoading(getActivity());
        }
    }

    @Override
    public void hideProgressDialog() {
        LoadingDialogManage.dismissLoading();
    }

    @Override
    public void showError(Throwable throwable) {
        if (BuildConfig.DEBUG) throwable.printStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            ToastUtil.s("网络连接超时");
            return;
        } else if (throwable instanceof UnknownHostException) {
            ToastUtil.s("网络请求失败");
            return;
        }
        ToastUtil.s(throwable.getMessage());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOGIN) {
            int eventId = 0;
            if (data != null)
                eventId = data.getIntExtra("eventId", 0);
            backFromLogin(eventId);//从登陆界面返回  登录成功
        }
    }

    protected void backFromLogin(int eventId) {

    }


    protected abstract int getLayoutId();

    protected abstract CommonToolbar setToolbar();

    protected abstract void initialize();
}
