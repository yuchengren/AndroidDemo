package com.yuchengren.demo.cache;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by yuchengren on 2016/9/27.
 * 用来存放Activity、Dialog等对象的集合栈
 */

public class UiStack {

    private static UiStack mUiStack ;
    //存放Activities对象的集合栈
    LinkedList<Activity>  mActivitiesList  = new LinkedList<>();

    private UiStack(){}

    public static UiStack getInstance(){
        if(mUiStack == null){
            synchronized (UiStack.class){
                if(mUiStack == null){
                    mUiStack = new UiStack();
                }
            }
        }
        return mUiStack;
    }

    public void addActivity(Activity activity){
        mActivitiesList.addLast(activity);
    }
    public void removeActivity(Activity activity){
        if(mActivitiesList != null || mActivitiesList.size() > 0){
            mActivitiesList.remove(activity);
        }
    }
    public void removeTopActivity(){
        if(mActivitiesList != null || mActivitiesList.size() > 0){
            mActivitiesList.removeLast();
        }
    }
    public Activity getTopActivity(){
        if(mActivitiesList != null || mActivitiesList.size() > 0){
            return mActivitiesList.getLast();
        }
        return null;
    }

    public void clearActivitiesStack(){
        for (Activity activity : mActivitiesList) {
            if(activity != null){
                activity.finish();
            }
        }
        mActivitiesList.clear();
    }

}
