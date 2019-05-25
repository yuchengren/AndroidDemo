package com.yuchengren.demo.app.widgettest.refreshview

import android.widget.TextView
import com.ycr.lib.ui.gridimage.BaseRecyclerAdapter
import com.ycr.lib.ui.gridimage.BaseRecyclerHolder
import com.yuchengren.demo.R

/**
 * created by yuchengren on 2019/5/23
 */
class TestRefreshApdater(dataList: MutableList<Int>?): BaseRecyclerAdapter<Int, BaseRecyclerHolder>(dataList, R.layout.item_test_refresh) {

    override fun convert(holder: BaseRecyclerHolder, position: Int, item: Int?) {
        holder.getView<TextView>(R.id.tvContent)?.text = item?.toString()
    }
}