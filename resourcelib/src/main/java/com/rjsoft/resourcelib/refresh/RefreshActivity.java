package com.rjsoft.resourcelib.refresh;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.mirkowu.library.BaseRVAdapter;
import com.mirkowu.library.listener.OnLoadMoreListener;
import com.rjsoft.resourcelib.Constants;
import com.rjsoft.resourcelib.R;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.widget.RefreshDelegateLayout;

import java.util.List;

/**
 * Created by DELL on 2017/7/28.
 */

public abstract class RefreshActivity<VM extends IBaseViewModel, B extends ViewDataBinding> extends BaseActivity<VM, B> implements OnLoadMoreListener {

    RefreshDelegateLayout mRefreshLayout;
    protected int mPage = 1;

    /**
     * initialize()方法 已被实现  需要调用super()
     */
    @Override
    protected void initialize() {
        mRefreshLayout = (RefreshDelegateLayout) findViewById(R.id.mRefreshLayout);
        if (mRefreshLayout != null)
            mRefreshLayout.setOnRefreshDelegateListener(() -> RefreshActivity.this.onRefresh());

    }

    public abstract void onRefresh();

    /**
     * 结束刷新
     */
    public void finishRefresh() {
        if (mRefreshLayout != null)
            mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        finishRefresh();
    }

    public void disableRefresh() {
        if (mRefreshLayout != null)
            mRefreshLayout.setEnableRefresh(false);
    }

    /**
     * 结束刷新
     * 上拉更多
     *
     * @param adapter
     * @param list
     */
    public void setLoadMore(BaseRVAdapter adapter, List<?> list) {
        finishRefresh();
        if (mPage == 1) {
            adapter.setData(list);
            if (list == null || list.size() == 0) {
                adapter.setEmptyView(View.inflate(this, R.layout.layout_empty, null));
            }
        } else {
            adapter.addData(list);
        }

        if (list == null || list.size() < Constants.PAGE_COUNT) {
            adapter.loadMoreEnd();
        } else {
            adapter.setOnLoadMoreListener(this);
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMore() {

    }
}
