package com.ycr.lib.ui.pullrefresh.smart

import com.ycr.lib.ui.pullrefresh.OnPullStateChangedListener
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener


/**
 * Created by yuchengren on 2019/06/14.
 */

class SmartRefreshOnMultiPurposeListener : OnMultiPurposeListener {

    private var pullListener: OnPullStateChangedListener? = null

    fun setPullListener(pullListener: OnPullStateChangedListener) {
        this.pullListener = pullListener
    }


    override fun onHeaderMoving(header: RefreshHeader, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {

    }

    override fun onHeaderReleased(header: RefreshHeader, headerHeight: Int, maxDragHeight: Int) {

    }

    override fun onHeaderStartAnimator(header: RefreshHeader, headerHeight: Int, extendHeight: Int) {

    }

    override fun onHeaderFinish(header: RefreshHeader, success: Boolean) {
        pullListener?.let {
            header.view.handler.postDelayed({ it.onPullUpRefreshFinish() }, 500)//classic的header有500ms的延迟
        }
    }

    override fun onFooterMoving(footer: RefreshFooter, isDragging: Boolean, percent: Float, offset: Int, footerHeight: Int, maxDragHeight: Int) {

    }

    override fun onFooterReleased(footer: RefreshFooter, footerHeight: Int, maxDragHeight: Int) {

    }


    override fun onFooterStartAnimator(footer: RefreshFooter, footerHeight: Int, extendHeight: Int) {

    }

    override fun onFooterFinish(footer: RefreshFooter, success: Boolean) {
        pullListener?.onPullDownLoadMoreFinish(success)
    }


    override fun onRefresh(refreshlayout: RefreshLayout) {
        pullListener?.onPullUpRefresh()
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pullListener?.onPullDownLoadMore()
    }
}
