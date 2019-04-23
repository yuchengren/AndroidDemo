package com.ycr.module.framework.view;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ycr.lib.ui.pullrefresh.IRefreshView;
import com.ycr.lib.ui.pullrefresh.smart.SmartRefreshView;


/**
 * Created by ye on 2018/5/29.
 */

public class KklLayoutInflator implements LayoutInflater.Factory2 {
    private AppCompatDelegate delegate;
    private static final String Refresh_Layout = IRefreshView.class.getName();

    public KklLayoutInflator(AppCompatDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view;
        if (TextUtils.equals(name, Refresh_Layout)) {
            view = new SmartRefreshView(context, attrs);
        } else {
            view = delegate.createView(parent, name, context, attrs);
        }
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }
}
