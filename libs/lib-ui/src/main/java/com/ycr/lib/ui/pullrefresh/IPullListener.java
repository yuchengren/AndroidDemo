package com.ycr.lib.ui.pullrefresh;

/**
 *
 */
public interface IPullListener {
    /**
     * 开始下拉刷新
     */
    void onPullRefresh();


    /**
     * 下拉刷新结束
     */
    void onPullRefreshFinish();

}
