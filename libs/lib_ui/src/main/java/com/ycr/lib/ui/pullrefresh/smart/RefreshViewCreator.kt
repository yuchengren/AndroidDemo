package com.ycr.lib.ui.pullrefresh.smart

import android.content.Context

import com.ycr.lib.ui.R
import com.ycr.lib.ui.pullrefresh.smart.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * Created by yuchengren on 2019/06/14.
 */
class RefreshViewCreator : DefaultRefreshHeaderCreator, DefaultRefreshFooterCreator {
    override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
        return ClassicsHeader(context)
    }

    override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
        val classicsFooter = ClassicsFooter(context)
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据哟"
        classicsFooter.setTextSizeTitle(16f)
        classicsFooter.setAccentColorId(R.color.text_color_medium)
        return classicsFooter
    }
}
