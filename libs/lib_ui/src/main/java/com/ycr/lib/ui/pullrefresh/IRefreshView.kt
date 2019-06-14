package com.ycr.lib.ui.pullrefresh

/**
 * 拉动刷新的接口
 * Created by yuchengren on 2019/06/14.
 */
interface IRefreshView {
    /**
     * 判断当前是否正在下拉刷新
     */
    val isPullDownRefreshing: Boolean

    /**
     * 是否正在上拉加载更多
     */
    val isPullUpLoadingMore: Boolean

    /**
     * 设置当前下拉刷新是否可用
     * true表示可用，false表示不可用
     */
    var isPullDownRefreshEnabled: Boolean

    /**
     * 设置当前上拉加载更多是否可用
     * true表示可用，false表示不可用
     */
    var isPullUpLoadMoreEnabled: Boolean

    /**
     * 是否没有更多数据
     */
    var noMoreData: Boolean

    /**
     * 数据全部加载没有更多后，footer是否可见
     */
    var isFooterVisibleWhenNoMoreData: Boolean

    /**
     * 自动触发下拉刷新
     * @param delayed 延迟时间
     */
    fun autoPullDownRefresh(delayed: Int)

    /**
     * 结束下拉刷新
     */
    fun finishPullDownRefresh(isSuccess: Boolean)

    fun finishPullDownRefresh(isSuccess: Boolean,noMoreData: Boolean)

    /**
     * 结束上拉加载更多
     */
    fun finishPullUpLoadMore(isSuccess: Boolean)

    fun finishPullUpLoadMore(isSuccess: Boolean,noMoreData: Boolean)

    /**
     * 设置下拉刷新监听
     */
    fun setOnPullDownRefreshListener(onPullDownRefreshListener: OnPullDownRefreshListener)

    /**
     * 设置上拉加载更多监听
     */
    fun setOnPullUpLoadMoreListener(onPullUpLoadMoreListener: OnPullUpLoadMoreListener)

    /**
     * 设置状态监听
     */
    fun setOnPullStateChangedListener(pullListener: OnPullStateChangedListener)

}
