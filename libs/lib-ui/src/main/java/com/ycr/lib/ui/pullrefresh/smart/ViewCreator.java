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

/**
 * Created by ye on 2017/8/8.
 */
public class ViewCreator implements DefaultRefreshHeaderCreator, DefaultRefreshFooterCreator {
    @NonNull
    @Override
    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        return new ClassicsHeader(context);
    }

    @NonNull
    @Override
    public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        return new ClassicsFooter(context);
    }
}
