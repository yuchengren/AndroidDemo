package com.yuchengren.mvp.app.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ycr.kernel.log.LogHelper;
import com.ycr.lib.changeskin.SkinManager;
import com.ycr.lib.changeskin.callback.ISkinChangingCallback;
import com.yuchengren.mvp.R;
import com.yuchengren.mvp.constant.SharePrefsKey;
import com.yuchengren.mvp.constant.SharePrefsValue;
import com.yuchengren.mvp.util.CrashHandler;
import com.yuchengren.mvp.util.SharePrefsUtil;
import com.yuchengren.mvp.util.ToastHelper;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Created by yuchengren on 2018/3/26.
 */

public class ChangeThemeActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String PATH_APK = Environment.getExternalStorageDirectory() + File.separator;

    private TextView tv_system_theme;
    private RadioButton rb_system_theme_red;
    private RadioButton rb_system_theme_blue;

    private TextView tv_apk_theme;
    private RadioButton rb_apk_theme_red;
    private RadioButton rb_apk_theme_blue;

    private TextView tv_changeskin;
    private TextView tv_clear_skin;
    private RadioButton rb_changeskin_red;
    private RadioButton rb_changeskin_blue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_switch);
        initViews();
        initListeners();
        initData();
    }

    protected void initViews() {
        tv_system_theme = (TextView) findViewById(R.id.tv_system_theme);
        rb_system_theme_red = (RadioButton) findViewById(R.id.rb_system_theme_red);
        rb_system_theme_blue = (RadioButton) findViewById(R.id.rb_system_theme_blue);

        tv_apk_theme = (TextView) findViewById(R.id.tv_apk_theme);
        rb_apk_theme_red = (RadioButton) findViewById(R.id.rb_apk_theme_red);
        rb_apk_theme_blue = (RadioButton) findViewById(R.id.rb_apk_theme_blue);

        tv_changeskin = (TextView) findViewById(R.id.tv_changeskin);
        tv_clear_skin = (TextView) findViewById(R.id.tv_clear_skin);
        rb_changeskin_red = (RadioButton) findViewById(R.id.rb_changeskin_red);
        rb_changeskin_blue = (RadioButton) findViewById(R.id.rb_changeskin_blue);
    }

    protected void initListeners() {
        rb_system_theme_red.setOnClickListener(this);
        rb_system_theme_blue.setOnClickListener(this);

        rb_apk_theme_red.setOnClickListener(this);
        rb_apk_theme_blue.setOnClickListener(this);

        rb_changeskin_red.setOnClickListener(this);
        rb_changeskin_blue.setOnClickListener(this);
        tv_clear_skin.setOnClickListener(this);
    }

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
            case R.id.rb_changeskin_red:
                changeRedSkin();
                break;
            case R.id.rb_changeskin_blue:
                changeBlueSkin();
                break;
            case R.id.tv_clear_skin:
                SkinManager.INSTANCE.clearPlugin();
                break;
            default:
                break;
        }
    }

    private void changeRedSkin() {
//        SkinManager.INSTANCE.changeSkin("red");
        SkinManager.INSTANCE.changeSkin(PATH_APK + "redskin.apk", "com.ycr.redskin", new ISkinChangingCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                ToastHelper.show("换肤红色成功！");
            }

            @Override
            public void onError(@NotNull Exception e) {
                LogHelper.e(e);
            }
        });
    }

    private void changeBlueSkin() {
//        SkinManager.INSTANCE.changeSkin("blue");
        SkinManager.INSTANCE.changeSkin(PATH_APK + "blueskin.apk", "com.ycr.blueskin", new ISkinChangingCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                ToastHelper.show("换肤蓝色成功！");
            }

            @Override
            public void onError(@NotNull Exception e) {
                LogHelper.e(e);
            }
        });
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
