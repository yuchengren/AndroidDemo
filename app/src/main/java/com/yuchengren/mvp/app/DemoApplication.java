package com.yuchengren.mvp.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Environment;

import com.ycr.kernel.log.LogHelper;
import com.ycr.kernel.log.config.LogConfig;
import com.ycr.kernel.log.config.LogFileConfig;
import com.ycr.kernel.log.constants.LogLevel;
import com.ycr.kernel.log.constants.LogPrinterType;
import com.ycr.kernel.log.engine.ILogEngine;
import com.ycr.lib.changeskin.SkinManager;
import com.yuchengren.mvp.constant.Constants;
import com.yuchengren.mvp.greendao.gen.DaoMaster;
import com.yuchengren.mvp.greendao.gen.DaoSession;
import com.yuchengren.mvp.util.CrashHandler;
import com.yuchengren.mvp.util.DataBaseHelper;
import com.yuchengren.mvp.util.OkHttpUtil;
import com.yuchengren.mvp.util.SharePrefsUtil;

import org.greenrobot.greendao.database.Database;

import okhttp3.OkHttpClient;

/**
 * Created by yuchengren on 2016/9/2.
 */
public class DemoApplication extends Application {

    private static DemoApplication mDemoApplication;
    /**
     * 默认初始化的OkHttpClient
     */
    private OkHttpClient mDefaultOkHttpClient;
    private DaoSession mDaoSession;

    public synchronized static DemoApplication getInstance(){
        if(mDemoApplication == null ){
            mDemoApplication = new DemoApplication();
        }
        return mDemoApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mDemoApplication = this;
        SharePrefsUtil.getInstance().init(getApplicationContext());
        CrashHandler.getInstance().init(getApplicationContext());
        initGreenDao();
        initOkHttp();
        initLog();
        initSkin();
    }

    private void initSkin() {
		SkinManager.INSTANCE.init(this);
    }

    private String getAppRootPath(){
        String appRootPath;
//        String packageName = getPackageName();
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
//            appRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getPackageName();
            appRootPath = getExternalFilesDir(null).getPath();
        }else{
            appRootPath = getFilesDir().getPath();
        }
        appRootPath += "/logs";
        return appRootPath;
    }

    private void initLog() {
		LogHelper.initAppModule("app").config(LogConfig.create(this)
                .setLogFileConfig(LogFileConfig.create(this).setFileRootPath(getAppRootPath()))
		        .setTagPre("mvp")
                .setEnabled(true)
                .setLevel(LogLevel.ERROR)
                .setLogPrinterTypes(LogPrinterType.CONSOLE,LogPrinterType.FILE));

        LogHelper.e("tag","msg");

        ILogEngine task = LogHelper.module("task");
        LogHelper.module("task").e("tag","msg");
    }

    private void initGreenDao() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext(), Constants.DB_NAME);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 初始化OkHttpClient
     */
    private void initOkHttp() {
        OkHttpUtil.getInstance().initClient();
        setDefaultOkHttpClient(OkHttpUtil.getInstance().getOkHttpClient());
    }

    public OkHttpClient getDefaultOkHttpClient() {
        if(mDefaultOkHttpClient == null){
            initOkHttp();
        }
        return mDefaultOkHttpClient;
    }

    public void setDefaultOkHttpClient(OkHttpClient mDefaultOkHttpClient) {
        this.mDefaultOkHttpClient = mDefaultOkHttpClient;
    }


}
