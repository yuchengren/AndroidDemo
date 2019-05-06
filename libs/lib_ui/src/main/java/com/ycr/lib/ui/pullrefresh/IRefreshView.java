package com.ycr.lib.ui.pullrefresh;

/**
 * 定义了拉动刷新的接口
 */
public interface IRefreshView {
    /**
     * 回滚等待时间
     */
    int REFRESHING_TIME = 500;

    void autoHeaderRefreshing(int delay);

    /**
     * 判断当前是否正在刷新
     *
     * @return
     */
    boolean isPullRefreshing();

    /**
     * 是否正在上拉加载更多
     *
     * @return
     */
    boolean isPullupLoading();

    /**
     * 设置当前下拉刷新是否可用
     *
     * @param pullRefreshEnabled true表示可用，false表示不可用
     */
    void setPullRefreshEnabled(boolean pullRefreshEnabled);

    /**
     * 设置当前上拉加载更多是否可用
     *
     * @param pullLoadEnabled true表示可用，false表示不可用
     */
    void setPullLoadEnabled(boolean pullLoadEnabled);

    /**
     * 判断当前下拉刷新是否可用
     *
     * @return true如果可用，false不可用
     */
    boolean isPullRefreshEnabled();

    /**
     * 判断上拉加载是否可用
     *
     * @return true可用，false不可用
     */
    boolean isPullLoadEnabled();

    /**
     * 结束下拉刷新
     */
    void onPullDownRefreshComplete(boolean isSuccess);

    /**
     * 结束上拉加载更多
     */
    void onPullUpRefreshComplete(boolean isSuccess);

    /**
     * 设置最后更新的时间文本
     *
     * @param label 文本
     */
    void setLastUpdatedLabel(CharSequence label);

    long getSmoothScrollDuration();

    /**
     * 显示更多
     *
     * @param hasMoreData     是否还有更多的数据
     * @param showHasMoreData 是否显示显示更多
     */
    void setHasMoreData(boolean hasMoreData, boolean showHasMoreData);

    /**
     * 设置刷新监听
     *
     * @param refreshListener
     */
    void setRefreshListener(IRefreshListener refreshListener);

    void setPullListener(IPullListener pullListener);
}
