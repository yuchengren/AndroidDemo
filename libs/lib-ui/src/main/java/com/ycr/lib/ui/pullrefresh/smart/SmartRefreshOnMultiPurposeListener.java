package com.ycr.lib.ui.pullrefresh.smart;

import android.support.annotation.NonNull;

import com.ycr.lib.ui.pullrefresh.IPullListener;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;


/**
 * Created by ye on 2018/3/26.
 */

public class SmartRefreshOnMultiPurposeListener implements OnMultiPurposeListener {
    private IPullListener pullListener;

    public SmartRefreshOnMultiPurposeListener() {
    }

    public void setPullListener(IPullListener pullListener) {
        this.pullListener = pullListener;
    }


    @Override
    public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

    }

    @Override
    public void onHeaderFinish(RefreshHeader header, boolean success) {
        if (pullListener != null) {
            header.getView().getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pullListener.onPullRefreshFinish();
                }
            }, 500);//classic的header有500ms的延迟
        }
    }

    @Override
    public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

    }


    @Override
    public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

    }

    @Override
    public void onFooterFinish(RefreshFooter footer, boolean success) {

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (pullListener != null) {
            pullListener.onPullRefresh();
        }
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }
}
