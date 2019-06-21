package com.ycr.module.base.view.pullrefresh

import android.content.Context
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.ycr.module.base.R

/**
 * created by yuchengren on 2019-06-18
 */
class MKRefreshHeader  @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        RelativeLayout(context, attrs, defStyleAttr),RefreshHeader {

    private val pullDownAnim: LottieAnimationView = LottieAnimationView(context)
    private val pullDownReleaseAnim: LottieAnimationView = LottieAnimationView(context)
    private val pullDownFinishAnim: LottieAnimationView? = null

    init {
        val animWidth = context.resources.getDimensionPixelOffset(R.dimen.pullrefesh_anim_width)
        val animHeight = context.resources.getDimensionPixelOffset(R.dimen.pullrefesh_anim_height)
        pullDownAnim.run {
            setAnimation(R.raw.pulldown)
            imageAssetsFolder = "pulldown"
            layoutParams = LayoutParams(animWidth,animHeight).apply {
                addRule(CENTER_IN_PARENT)
            }
        }
        addView(pullDownAnim)

        pullDownReleaseAnim.run {
            setAnimation(R.raw.pullrefresh)
            imageAssetsFolder = "pullrefresh"
            layoutParams = LayoutParams(animWidth,animHeight).apply {
                addRule(CENTER_IN_PARENT)
            }
            visibility = View.GONE
        }
        addView(pullDownReleaseAnim)
    }


    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        pullDownAnim.run {
            if(isAnimating){
                cancelAnimation()
                visibility = View.GONE
            }
        }

        pullDownReleaseAnim.run {
            if(isAnimating){
                cancelAnimation()
                visibility = View.GONE
            }
        }
        return 400
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun getView(): View {
        return this
    }

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        when(newState){
            //下拉还未松手时
            RefreshState.PullDownToRefresh -> {}
            //下拉松手时
            RefreshState.RefreshReleased -> {
                pullDownAnim.run {
                    visibility = View.GONE
                    if(isAnimating){
                        cancelAnimation()
                    }
                }

                pullDownReleaseAnim.run {
                    visibility = View.VISIBLE
                    repeatCount = LottieDrawable.INFINITE
                    playAnimation()
                }
            }
            RefreshState.ReleaseToRefresh ->{}
            RefreshState.RefreshFinish ->{}
        }

    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        if(pullDownReleaseAnim.isAnimating){
            return
        }
        pullDownReleaseAnim.visibility = View.GONE
        if(!isDragging){
            return
        }
        pullDownAnim.visibility = View.VISIBLE
        val progress = when{
            percent < 0 -> 0f
            percent > 1 -> 1f
            else -> percent
        }
        pullDownAnim.progress = progress
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(pullDownAnim.visibility == View.VISIBLE){
            pullDownAnim.resumeAnimation()
        }
        if(pullDownReleaseAnim.visibility == View.VISIBLE){
            pullDownReleaseAnim.resumeAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(pullDownAnim.visibility == View.VISIBLE){
            pullDownAnim.pauseAnimation()
        }
        if(pullDownReleaseAnim.visibility == View.VISIBLE){
            pullDownReleaseAnim.pauseAnimation()
        }
    }
}