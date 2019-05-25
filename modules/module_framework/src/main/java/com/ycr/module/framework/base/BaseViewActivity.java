package com.ycr.module.framework.base;

import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.TextView;

import com.ycr.module.framework.R;

import org.jetbrains.annotations.Nullable;

/**
 * created by yuchengren on 2019/4/13
 */
abstract public class BaseViewActivity extends SuperActivity implements IAboveView {

    protected boolean isEmpty = true;//界面数据是否为空

    protected View headerView;
    protected TextView tvBack;
    protected TextView tvTitle;
    private static final int UNDEFINED = -1;

    @CallSuper
    @Override
    public void bindView(View rootView) {
        super.bindView(rootView);
        headerView = findViewById(R.id.headerView);
        tvBack = findViewById(R.id.tvBack);
        tvTitle = findViewById(R.id.tvTitle);

        if (tvBack != null) {
            tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        if (tvTitle != null) {
            tvTitle.setText(getTitleText());
        }
    }

    protected String getTitleText() {
        int titleId = getTitleId();
        return titleId == UNDEFINED ? "" : getString(titleId);
    }

    protected int getTitleId() {
        return UNDEFINED;
    }

    private int showLoadingCount = 0;

    @Override
    public void showLoading(@Nullable String msg, boolean isEmpty) {

    }

    @Override
    public void dismissLoading(boolean isSuccess) {

    }
}
