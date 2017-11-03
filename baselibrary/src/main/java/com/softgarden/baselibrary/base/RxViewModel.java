package com.softgarden.baselibrary.base;


/**
 * 引入 IBaseDisplay mView  控制RxJava生命周期
 */
public class RxViewModel<T extends IBaseDisplay> implements IBaseViewModel<T> {
    protected T mView;

    @Override
    public void attachView(T display) {
        this.mView = display;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
