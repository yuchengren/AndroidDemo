package com.ycr.lib.ui.pullrefresh.smart;

import android.content.Context;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ycr.lib.ui.R;

/**
 * Created by ye on 2017/8/8.
 */
public class RefreshViewCreator implements DefaultRefreshHeaderCreator, DefaultRefreshFooterCreator {
    @NonNull
    @Override
    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        return new ClassicsHeader(context);
    }

    @NonNull
    @Override
    public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        ClassicsFooter classicsFooter = new ClassicsFooter(context);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据哟";
        classicsFooter.setTextSizeTitle(16);
        classicsFooter.setAccentColorId(R.color.text_color_medium);
        return classicsFooter;
    }
}
