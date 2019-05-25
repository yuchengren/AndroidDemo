package com.ycr.lib.ui.pullrefresh.smart;

import android.content.Context;
import android.util.AttributeSet;

import com.ycr.lib.ui.pullrefresh.IPullListener;
import com.ycr.lib.ui.pullrefresh.IRefreshListener;
import com.ycr.lib.ui.pullrefresh.IRefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by ye on 2018/2/4.
 */

public class SmartRefreshView extends SmartRefreshLayout implements IRefreshView {
    private SmartRefreshOnMultiPurposeListener smartRefreshOnMultiPurposeListener;

    public SmartRefreshView(Context context) {
        super(context);
        init();
    }

    public SmartRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setPullLoadEnabled(false);
        setPullRefreshEnabled(true);
        smartRefreshOnMultiPurposeListener = new SmartRefreshOnMultiPurposeListener();
        super.setOnMultiPurposeListener(smartRefreshOnMultiPurposeListener);
    }

    @Override
    public void autoHeaderRefreshing(int wait) {
//        resetStatus();
        super.autoRefresh(wait);
    }

    @Override
    public boolean isPullRefreshing() {
        return getState()== RefreshState.Refreshing;
    }

    @Override
    public boolean isPullupLoading() {
        return getState()== RefreshState.Loading;
    }

    @Override
    public void setPullRefreshEnabled(boolean pullRefreshEnabled) {
        super.setEnableRefresh(pullRefreshEnabled);
    }

    @Override
    public void setPullLoadEnabled(boolean pullLoadEnabled) {
        super.setEnableLoadMore(pullLoadEnabled);
    }

    @Override
    public boolean isPullRefreshEnabled() {
        return mEnableRefresh;
    }

    @Override
    public boolean isPullLoadEnabled() {
        return mEnableLoadMore;
    }

    @Override
    public void onPullDownRefreshComplete(boolean isSuccess) {
        super.finishRefresh(isSuccess);
    }

    @Override
    public void onPullUpRefreshComplete(boolean isSuccess) {
        super.finishLoadMore(isSuccess);
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {

    }

    @Override
    public long getSmoothScrollDuration() {
        return 400;
    }

    @Override
    public void setHasMoreData(boolean hasMoreData, boolean showHasMoreData) {
        super.setNoMoreData(!hasMoreData);//设置之后，将不会再触发加载事件
    }


    @Override
    public void setRefreshListener(final IRefreshListener refreshListener) {
        if (refreshListener == null) {
            return;
        }
        super.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshListener.onPullrefresh(SmartRefreshView.this);
            }
        });
        super.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshListener.onLoadMore(SmartRefreshView.this);
            }
        });
    }

    @Override
    public void setPullListener(IPullListener pullListener) {
        if (smartRefreshOnMultiPurposeListener != null) {
            smartRefreshOnMultiPurposeListener.setPullListener(pullListener);
        }
    }

}
