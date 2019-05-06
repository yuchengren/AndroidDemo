package com.ycr.lib.ui.pullrefresh;

/**
 * Created by ye on 2017/12/28.
 */

public interface IRefreshListener {
    /**
     *
     */
    void onPullrefresh(IRefreshView pullToRefresh);

    /**
     *
     */
    void onLoadMore(IRefreshView pullToRefresh);
}
