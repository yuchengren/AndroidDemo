package com.ycr.kernel.union.helper;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

/**
 * created by yuchengren on 2019/4/22
 */
public class ContextHelper {
    private static Context context;
    private static Application application;

    public static Context getContext(){
        return context;
    }

    public static Application getApplication(){
        return application;
    }

    public static void  doInit(Application application){
        ContextHelper.application = application;
        context = application.getApplicationContext();
    }

    public static Resources getResources(){
        return context.getResources();
    }

    public static String getString(int resId,Object... args){
        return context.getString(resId,args);
    }

    public static int getColor(int resId){
        return ContextCompat.getColor(context,resId);
    }

    public static int getDimenPixel(int resId){
        return getResources().getDimensionPixelSize(resId);
    }

}
