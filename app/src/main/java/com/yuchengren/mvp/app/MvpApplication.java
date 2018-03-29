package com.yuchengren.mvp.app;

import android.app.Application;
import android.util.Log;

import com.yuchengren.mvp.constant.Constants;
import com.yuchengren.mvp.greendao.gen.DaoMaster;
import com.yuchengren.mvp.greendao.gen.DaoSession;
import com.yuchengren.mvp.util.CrashHandler;
import com.yuchengren.mvp.util.DataBaseHelper;
import com.yuchengren.mvp.util.LogHelper;
import com.yuchengren.mvp.util.OkHttpUtil;
import com.yuchengren.mvp.util.SharePrefsUtil;

import org.greenrobot.greendao.database.Database;

import okhttp3.OkHttpClient;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by yuchengren on 2016/9/2.
 */
public class MvpApplication extends Application {

    private static MvpApplication mMvpApplication;
    /**
     * 默认初始化的OkHttpClient
     */
    private OkHttpClient mDefaultOkHttpClient;
    private DaoSession mDaoSession;

    public synchronized static MvpApplication getInstance(){
        if(mMvpApplication == null ){
            mMvpApplication = new MvpApplication();
        }
        return mMvpApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mMvpApplication = this;
        LogHelper.isDebug = true;
        LogHelper.mLogInFileLevel  = Log.DEBUG;
        SharePrefsUtil.getInstance().init(getApplicationContext());
        CrashHandler.getInstance().init(getApplicationContext());
        initGreenDao();
        initOkHttp();
        initSupportSkin();
    }

    private void initSupportSkin() {
        SkinCompatManager.init(this)                          // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())  // material design 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())      // CardView 控件换肤初始化[可选]
                .loadSkin();                                  // 加载当前皮肤库(保存在SharedPreferences中)
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
