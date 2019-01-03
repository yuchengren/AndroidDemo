package com.ycr.lib.ui.view.ninegrid

import android.content.Context
import android.graphics.Canvas
import android.support.annotation.Nullable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ycr.lib.ui.R

/**
 * Created by yuchengren on 2018/12/27.
 */
class GridRecyclerView : RecyclerView {
    var maxItemCount = 9
    var columnsNum = 3
    var itemWidth = 0
    var itemHorizontalSpacing = 0
    var itemVerticalSpacing = 0

    var itemCornerRadius = 0
    var itemStrokeVisible = false
    var itemStrokeWidth = 0
    var itemStrokeColor = 0
    var itemScaleType = -1

    var plusScaleType = -1
    var plusCornerRadius = 0
    var plusStrokeVisible = false
    var plusStrokeWidth = 0
    var plusStrokeColor = 0

    var plusDrawable: Int = 0
    var plusEnabled = true

    var onEventListener: OnEventListener? = null
    var gridImageAdapter: GridImageAdapter? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.GridRecyclerView, defStyleAttr, 0)
        typeArray.run {
            maxItemCount = getInt(R.styleable.GridRecyclerView_maxItemCount, 9)
            columnsNum = getInt(R.styleable.GridRecyclerView_columnsNum, 3)

            itemWidth = getDimensionPixelSize(R.styleable.GridRecyclerView_itemWidth, 0)
            itemHorizontalSpacing = getDimensionPixelSize(R.styleable.GridRecyclerView_itemHorizontalSpacing, 0)
            itemVerticalSpacing = getDimensionPixelSize(R.styleable.GridRecyclerView_itemVerticalSpacing, 0)


            itemCornerRadius = getDimensionPixelSize(R.styleable.GridRecyclerView_itemCornerRadius, 0)
            itemStrokeVisible = getBoolean(R.styleable.GridRecyclerView_itemStrokeVisible, false)
            itemStrokeWidth = getDimensionPixelSize(R.styleable.GridRecyclerView_itemStrokeWidth, 0)
            itemStrokeColor = getColor(R.styleable.GridRecyclerView_itemStrokeColor, 0)
            itemScaleType = getInt(R.styleable.GridRecyclerView_itemScaleType, -1)

            plusScaleType = getInt(R.styleable.GridRecyclerView_plusScaleType, 5)
            plusCornerRadius = getDimensionPixelSize(R.styleable.GridRecyclerView_plusCornerRadius, 0)
            plusStrokeVisible = getBoolean(R.styleable.GridRecyclerView_plusStrokeVisible, false)
            plusStrokeWidth = getDimensionPixelSize(R.styleable.GridRecyclerView_plusStrokeWidth, 0)
            plusStrokeColor = getColor(R.styleable.GridRecyclerView_plusStrokeColor, 0)

            plusDrawable = getResourceId(R.styleable.GridRecyclerView_plusDrawable, 0)
            plusEnabled = getBoolean(R.styleable.GridRecyclerView_plusEnabled, true)

            recycle()
        }

        if (layoutManager == null) {
            layoutManager = GridLayoutManager(context, columnsNum)
        }
        addItemDecoration(GridItemDecoration(itemHorizontalSpacing, itemVerticalSpacing, columnsNum, false))
        if (gridImageAdapter == null) {
            gridImageAdapter = GridImageAdapter(null)
            adapter = gridImageAdapter

        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (itemWidth == 0) {
            itemWidth = ((w - paddingLeft - paddingRight) - (columnsNum - 1) * itemHorizontalSpacing) / columnsNum
        }
    }

    fun setNewData(data: MutableList<Pair<String, Boolean>>) {
        gridImageAdapter?.setNewData(data)
    }

    fun addData(data: MutableList<Pair<String, Boolean>>) {
        gridImageAdapter?.addData(data)
    }

    fun addData(data: Pair<String, Boolean>) {
        gridImageAdapter?.addData(data)
    }

    fun removeItem(position: Int) {
        gridImageAdapter?.remove(position)
    }

    private fun getImageScaleType(type: Int): ImageView.ScaleType {
        return when (type) {
            0 -> ImageView.ScaleType.MATRIX
            1 -> ImageView.ScaleType.FIT_XY
            2 -> ImageView.ScaleType.FIT_START
            3 -> ImageView.ScaleType.FIT_CENTER
            4 -> ImageView.ScaleType.FIT_END
            5 -> ImageView.ScaleType.CENTER
            6 -> ImageView.ScaleType.CENTER_CROP
            7 -> ImageView.ScaleType.CENTER_INSIDE
            else -> ImageView.ScaleType.CENTER
        }
    }

    inner class GridImageAdapter(data: MutableList<Pair<String, Boolean>>?) :
            BaseQuickAdapter<Pair<String, Boolean>, BaseViewHolder>(R.layout.item_grid_image, data) {

        override fun getItemCount(): Int {
            var itemCount = data?.size ?: 0
            if (plusEnabled && itemCount < maxItemCount) {
                itemCount += 1
            }
            return itemCount
        }

        override fun getItem(position: Int): Pair<String, Boolean>? {
            if (isPlusItem(position)) {
                return null
            }
            return super.getItem(position)
        }

        fun isPlusItem(position: Int): Boolean {
            return plusEnabled && super.getItemCount() < maxItemCount && position == itemCount - 1
        }

        override fun convert(helper: BaseViewHolder?, item: Pair<String, Boolean>?) {

            if (helper == null) {
                return
            }
            val gridImageView = helper.getView<GridImageView>(R.id.gridImageView)
            if (item == null) {
                gridImageView.run {
                    strokeVisible = plusStrokeWidth > 0
                    strokeWidth = plusStrokeWidth
                    strokeColor = plusStrokeColor
                    cornerRadius = plusCornerRadius
                    scaleType = getImageScaleType(plusScaleType)
                    setImageResource(plusDrawable)
                }
            } else {
                gridImageView.run {
                    strokeVisible = itemStrokeWidth > 0 && item.second
                    strokeWidth = itemStrokeWidth
                    strokeColor = itemStrokeColor
                    cornerRadius = itemCornerRadius
                    scaleType = getImageScaleType(itemScaleType)
                }
                onEventListener?.loadUrl(item.first, gridImageView)
            }
        }
    }

    interface OnEventListener {
        fun loadUrl(url: String, view: ImageView)
    }


}