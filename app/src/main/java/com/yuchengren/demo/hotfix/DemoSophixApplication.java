package com.yuchengren.demo.hotfix;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.yuchengren.demo.BuildConfig;
import com.yuchengren.demo.app.DemoApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 注意：此类下 只能调用android系统API，使用其他自定义的库API会报错
 * created by yuchengren on 2019/5/25
 */
public class DemoSophixApplication extends SophixApplication {

    private final String TAG = "DemoSophixApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。

    @Keep
    @SophixEntry(DemoApplication.class)
    static class RealApplicationStub {}

    public static final String idSecret = "";
    public static final String appSecret = "";
    public static final String rsaSecret = "";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        List<String> tags = new ArrayList<>();
        tags.add(BuildConfig.FLAVOR);
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(idSecret, appSecret, rsaSecret)
                .setTags(tags)
                .setEnableDebug(BuildConfig.DEBUG)
//                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.i(TAG, "sophix load,code = " + code + ",info = " + info);
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                            HotFixManager.INSTANCE.setPatchVersion(handlePatchVersion);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}
