package com.ycr.lib.ui.pullrefresh;

import android.support.annotation.NonNull;

import com.ycr.lib.ui.pullrefresh.IRefreshView;

/**
 * created by yuchengren on 2019-06-14
 */
public interface OnPullUpLoadMoreListener {
    /**
     * 上拉加载更多时
     * @param pullRefreshView
     */
    void onPullUpLoadMore(@NonNull IRefreshView pullRefreshView);
}
