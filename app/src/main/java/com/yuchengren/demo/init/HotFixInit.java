package com.yuchengren.demo.init;

import android.content.Context;


import com.taobao.sophix.SophixManager;
import com.ycr.kernel.union.AbstractAppInit;
import com.yuchengren.demo.BuildConfig;

/**
 * Created by yuchengren on 2019/05/25
 */

public class HotFixInit extends AbstractAppInit {

    public HotFixInit(String name) {
        super(name);
    }

    @Override
    public boolean doInit(Context context){
        if (!BuildConfig.DEBUG) {  //调试模式下不加载patch，否则会导致各种异常
            SophixManager.getInstance().queryAndLoadNewPatch();
            return true;
        }
        return false;
    }
}
