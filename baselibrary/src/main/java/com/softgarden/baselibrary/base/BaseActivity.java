package com.softgarden.baselibrary.base;

import android.databinding.ViewDataBinding;

import com.softgarden.baselibrary.base.databinding.DataBindingActivity;
import com.softgarden.baselibrary.utils.InstanceUtil;

import java.lang.reflect.ParameterizedType;

/**
 * Activity的基类
 * Created by Administrator on 2015/6/2.
 */

public abstract class BaseActivity<T extends IBasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {
    protected T mPresenter;

    @Override
    protected void initPresenter() {
        if (this instanceof IBaseDisplay
                && this.getClass().getGenericSuperclass() instanceof ParameterizedType
                && ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0];//获取Presenter的class
            mPresenter = InstanceUtil.getInstance(mPresenterClass);
            if (mPresenter != null) mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }


}
