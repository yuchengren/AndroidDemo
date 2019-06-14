package com.ycr.lib.ui.pullrefresh.smart

import android.content.Context
import android.support.annotation.Nullable
import android.util.AttributeSet
import com.ycr.lib.ui.pullrefresh.*

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState

/**
 * 实现了统一刷新接口的拉动刷新控件
 * Created by yuchengren on 2018/06/14.
 */

class SmartRefreshView @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        SmartRefreshLayout(context, attrs, defStyleAttr) , IRefreshView {

    companion object{
        const val ANIMATION_DURATION = 300 //动画时长
    }

    private val smartRefreshOnMultiPurposeListener: SmartRefreshOnMultiPurposeListener =
            SmartRefreshOnMultiPurposeListener()

    override val isPullDownRefreshing: Boolean
        get() = state == RefreshState.Refreshing

    override val isPullUpLoadingMore: Boolean
        get() = state == RefreshState.Loading

    override var isPullDownRefreshEnabled: Boolean
        get() = mEnableRefresh
        set(value) {
            super.setEnableRefresh(value)
        }

    override var isPullUpLoadMoreEnabled: Boolean
        get() = mEnableLoadMore
        set(value) {
            super.setEnableLoadMore(value)
        }

    override var noMoreData: Boolean
        get() = mFooterNoMoreData
        set(value) {
            super.setNoMoreData(value)
        }

    override var isFooterVisibleWhenNoMoreData: Boolean
        get() = mEnableFooterFollowWhenNoMoreData
        set(value) {
            super.setEnableFooterFollowWhenNoMoreData(value)
        }

    init{
        super.setOnMultiPurposeListener(smartRefreshOnMultiPurposeListener)
    }

    override fun autoPullDownRefresh(delayed: Int) {
        super.autoRefresh(delayed)
    }

    override fun finishPullDownRefresh(isSuccess: Boolean) {
        super.finishRefresh(isSuccess)
    }

    override fun finishPullDownRefresh(isSuccess: Boolean, noMoreData: Boolean) {
        val passTime = System.currentTimeMillis() - mLastOpenTime
        super.finishRefresh(if (isSuccess) Math.min(Math.max(0, ANIMATION_DURATION - passTime.toInt()), ANIMATION_DURATION) else 0, isSuccess, noMoreData)//保证加载动画有300毫秒的时间
    }

    override fun finishPullUpLoadMore(isSuccess: Boolean) {
        super.finishLoadMore(isSuccess)
    }

    override fun finishPullUpLoadMore(isSuccess: Boolean, noMoreData: Boolean) {
        val passTime = System.currentTimeMillis() - mLastOpenTime
        super.finishLoadMore(if (isSuccess) Math.min(Math.max(0, ANIMATION_DURATION - passTime.toInt()), ANIMATION_DURATION) else 0, isSuccess, noMoreData)
    }

    override fun setOnPullDownRefreshListener(onPullDownRefreshListener: OnPullDownRefreshListener) {
        super.setOnRefreshListener { onPullDownRefreshListener.onPullDownRefresh(this) }
    }

    override fun setOnPullUpLoadMoreListener(onPullUpLoadMoreListener: OnPullUpLoadMoreListener) {
        super.setOnLoadMoreListener { onPullUpLoadMoreListener.onPullUpLoadMore(this) }
    }

    override fun setOnPullStateChangedListener(pullListener: OnPullStateChangedListener) {
        smartRefreshOnMultiPurposeListener.setPullListener(pullListener)
    }
}
