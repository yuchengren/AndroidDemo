package com.yuchengren.mvp.app.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.presenter.abs.Presenter;
import com.yuchengren.mvp.app.ui.activity.Base.SuperActivity;
import com.yuchengren.mvp.constant.SharePrefsKey;
import com.yuchengren.mvp.constant.SharePrefsValue;
import com.yuchengren.mvp.util.CrashHandler;
import com.yuchengren.mvp.util.SharePrefsUtil;

/**
 * Created by yuchengren on 2018/3/26.
 */

public class ThemeSwitchActivity extends SuperActivity<Presenter> implements View.OnClickListener{

    private TextView tv_system_theme;
    private RadioButton rb_system_theme_red;
    private RadioButton rb_system_theme_blue;

    private TextView tv_apk_theme;
    private RadioButton rb_apk_theme_red;
    private RadioButton rb_apk_theme_blue;

    private TextView tv_support_theme;
    private RadioButton rb_support_theme_red;
    private RadioButton rb_support_theme_blue;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_theme_switch;
    }

    @Override
    protected void initViews() {
        tv_system_theme = (TextView) findViewById(R.id.tv_system_theme);
        rb_system_theme_red = (RadioButton) findViewById(R.id.rb_system_theme_red);
        rb_system_theme_blue = (RadioButton) findViewById(R.id.rb_system_theme_blue);

        tv_apk_theme = (TextView) findViewById(R.id.tv_apk_theme);
        rb_apk_theme_red = (RadioButton) findViewById(R.id.rb_apk_theme_red);
        rb_apk_theme_blue = (RadioButton) findViewById(R.id.rb_apk_theme_blue);

        tv_support_theme = (TextView) findViewById(R.id.tv_support_theme);
        rb_support_theme_red = (RadioButton) findViewById(R.id.rb_support_theme_red);
        rb_support_theme_blue = (RadioButton) findViewById(R.id.rb_support_theme_blue);
    }

    @Override
    protected void initListeners() {
        rb_system_theme_red.setOnClickListener(this);
        rb_system_theme_blue.setOnClickListener(this);

        rb_apk_theme_red.setOnClickListener(this);
        rb_apk_theme_blue.setOnClickListener(this);

        rb_support_theme_red.setOnClickListener(this);
        rb_support_theme_blue.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        TypedArray typedArray = obtainStyledAttributes(new int[]{R.attr.switch_text_color, R.attr.switch_text_size, R.attr.switch_text,R.attr.switch_drawable});
        tv_system_theme.setTextColor(typedArray.getColor(0, 0x000000));
        tv_system_theme.setTextSize(typedArray.getDimensionPixelSize(1,10));
        tv_system_theme.setText(typedArray.getString(2));
        tv_system_theme.setCompoundDrawablesWithIntrinsicBounds(null,null,typedArray.getDrawable(3),null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_system_theme_red:
                switchSystemTheme(SharePrefsValue.Theme.RED);
                break;
            case R.id.rb_system_theme_blue:
                switchSystemTheme(SharePrefsValue.Theme.BLUE);
                break;
            case R.id.rb_apk_theme_red:
                switchApkTheme("com.ycr.redskin");
                break;
            case R.id.rb_apk_theme_blue:
                switchApkTheme("com.ycr.blueskin");
                break;
            default:
                break;
        }
    }

    private void switchSystemTheme(String themeStyle) {
        SharePrefsUtil.getInstance().putString(SharePrefsKey.THEME_STYLE,themeStyle);
        CrashHandler.getInstance().exitApp();
    }

    private void switchApkTheme(String remotePackageName) {
        try {
            Context remoteContext = createPackageContext(remotePackageName,CONTEXT_IGNORE_SECURITY);
            Resources remoteResources = remoteContext.getResources();
            tv_apk_theme.setTextColor(remoteResources.getColor(remoteResources.getIdentifier("test_color","color",remotePackageName)));
            tv_apk_theme.setTextSize(remoteResources.getDimensionPixelSize(remoteResources.getIdentifier("test_size","dimen",remotePackageName)));
            tv_apk_theme.setText(remoteResources.getString(remoteResources.getIdentifier("test_text","string",remotePackageName)));
            tv_apk_theme.setCompoundDrawablesWithIntrinsicBounds(null,null,remoteResources.getDrawable(remoteResources.getIdentifier("test_drawable","drawable",remotePackageName)),null);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
