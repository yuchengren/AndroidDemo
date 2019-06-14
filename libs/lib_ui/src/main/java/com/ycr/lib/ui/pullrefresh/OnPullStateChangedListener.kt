package com.ycr.lib.ui.pullrefresh

/**
 * 状态发生变更的监听器
 * Created by yuchengren on 2019/06/14.
 */
interface OnPullStateChangedListener {
    /**
     * 下拉刷新开始时
     */
    fun onPullUpRefresh()

    /**
     * 下拉刷新结束时
     */
    fun onPullUpRefreshFinish()

    /**
     * 上拉加载更多开始时
     */
    fun onPullDownLoadMore()

    /**
     * 上拉加载更多结束时
     * @param isSuccess
     */
    fun onPullDownLoadMoreFinish(isSuccess: Boolean)

}
