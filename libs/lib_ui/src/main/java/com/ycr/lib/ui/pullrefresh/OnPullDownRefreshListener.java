package com.ycr.lib.ui.pullrefresh;

import android.support.annotation.NonNull;

import com.ycr.lib.ui.pullrefresh.IRefreshView;

/**
 * created by yuchengren on 2019-06-14
 */
public interface OnPullDownRefreshListener {
    /**
     * 下拉刷新时
     * @param pullRefreshView
     */
    void onPullDownRefresh(@NonNull IRefreshView pullRefreshView);
}
