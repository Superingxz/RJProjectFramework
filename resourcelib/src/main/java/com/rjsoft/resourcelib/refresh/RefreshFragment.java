package com.rjsoft.resourcelib.refresh;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.mirkowu.library.BaseRVAdapter;
import com.mirkowu.library.listener.OnLoadMoreListener;
import com.rjsoft.resourcelib.Constants;
import com.rjsoft.resourcelib.R;
import com.softgarden.baselibrary.base.BaseFragment;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.widget.RefreshDelegateLayout;

import java.util.List;

/**
 * Created by DELL on 2017/7/28.
 */

public abstract class RefreshFragment<VM extends IBaseViewModel, B extends ViewDataBinding> extends BaseFragment<VM, B> implements OnLoadMoreListener {

    RefreshDelegateLayout mRefreshLayout;
    protected int mPage = 1;

    /**
     * initView 已被实现  需要调用super()
     */
    @Override
    protected void initialize() {
        mRefreshLayout = (RefreshDelegateLayout) mView.findViewById(R.id.mRefreshLayout);
        if (mRefreshLayout != null)
            mRefreshLayout.setOnRefreshDelegateListener(() -> RefreshFragment.this.onRefresh());
    }

    public abstract void onRefresh();

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

    public void setLoadMore(BaseRVAdapter adapter, List<?> list) {
        finishRefresh();
        if (mPage == 1) {
            adapter.setData(list);
            if (list == null || list.size() == 0) {
                adapter.setEmptyView(View.inflate(getActivity(), R.layout.layout_empty, null));
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
