package com.ycr.lib.changeskin

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.AsyncTask
import android.text.TextUtils
import android.view.View
import com.ycr.lib.changeskin.attr.SkinAttrSupport
import com.ycr.lib.changeskin.attr.SkinView
import com.ycr.lib.changeskin.callback.ISkinChangingCallback
import com.ycr.lib.changeskin.utils.SkinPrefsHelper
import java.io.File

/**
 * 换肤的管理类
 * 需要换肤则需在xml里增加如下格式android:tag="skin:background=test_drawable|textColor=test_color"
 * Created by yuchengren on 2018/8/28.
 */
object SkinManager {

    private lateinit var mContext:Context
    private lateinit var mResourceManager: ResourceManager
    private var mIsUsePlugin: Boolean = false
    private var mSuffix: String? = ""
    private  var activities: MutableList<Activity> = mutableListOf()

    fun init(context: Context){
       this.mContext = context.applicationContext

        val pluginPath = SkinPrefsHelper.getPluginPath(mContext)
        val pluginPkg = SkinPrefsHelper.getPluginPkg(mContext)
        mSuffix = SkinPrefsHelper.getPluginSuffix(mContext)

        if(!isValidPluginParams(pluginPath,pluginPkg)){
            return
        }

        try {
            loadPlugin(pluginPath,pluginPkg,mSuffix)
        }catch (e: Exception){
            SkinPrefsHelper.clear(mContext)
            e.printStackTrace()
        }
    }

    /**
     * 加载皮肤
     */
    private fun loadPlugin(pluginPath: String?, pluginPkg: String?, pluginSuffix: String?) {
        val assetManager = AssetManager::class.java.newInstance()
        val method = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
        method.invoke(assetManager,pluginPath)

        val resources = Resources(assetManager,mContext.resources.displayMetrics,mContext.resources.configuration)
        mResourceManager = ResourceManager(resources,pluginPkg,pluginSuffix)
        mIsUsePlugin = true
    }

    private fun isValidPluginParams(pluginPath: String?, pluginPkg: String?):Boolean{
        if(TextUtils.isEmpty(pluginPath) || TextUtils.isEmpty(pluginPkg)){
            return false
        }
        if(!File(pluginPath).exists()){
            return false
        }
        if(!pluginPkg.equals(getPackageInfo(pluginPath)?.packageName)){
            return false
        }
        return true
    }

    private fun getPackageInfo(pluginPath: String?): PackageInfo?{
        return mContext.packageManager.getPackageArchiveInfo(pluginPath, PackageManager.GET_ACTIVITIES)
    }

    fun getResourceManager(): ResourceManager{
        if(!mIsUsePlugin){
            mResourceManager = ResourceManager(mContext.resources, mContext.packageName, mSuffix)
        }
        return mResourceManager
    }

    /**
     * 应用内换肤(即采用资源名+后缀名获取资源）
     */
    fun changeSkin(pluginSuffix: String?){
        clearPluginInfo()
        mSuffix = pluginSuffix
        SkinPrefsHelper.putPluginSuffix(mContext, mSuffix)
        notifyChangeListeners()
    }

    fun changeSkin(pluginPath: String?, pluginPkg: String?,callback: ISkinChangingCallback?){
        changeSkin(pluginPath,pluginPkg,"",callback)
    }

    fun changeSkin(pluginPath: String?, pluginPkg: String?, pluginSuffix: String?,callback: ISkinChangingCallback?){
        callback?.onStart()
        if(!isValidPluginParams(pluginPath,pluginPkg)){
            callback?.onError(IllegalArgumentException("pluginPath is $pluginPath,pluginPkg is $pluginPkg"))
            return
        }

        object: AsyncTask<Void,Void,Boolean>(){
            override fun doInBackground(vararg params: Void?): Boolean {
                try {
                    loadPlugin(pluginPath,pluginPkg,pluginSuffix)
                    return true
                }catch (e: Exception){
                    e.printStackTrace()
                }
                return false
            }

            override fun onPostExecute(result: Boolean) {
                super.onPostExecute(result)
                if(!result){
                    callback?.onError(RuntimeException("loadPlugin error,pluginPath is $pluginPath,pluginPkg is $pluginPkg"))
                    return
                }
                try {
                    updatePluginInfo(pluginPath,pluginPkg,pluginSuffix)
                    notifyChangeListeners()
                    callback?.onCompleted()
                }catch (e: Exception){
                    callback?.onError(e)
                }
            }
        }.execute()
    }

    /**
     * 为View注入皮肤（非Activity布局下的View,如ListView的Item）
     */
    fun injectSkin(view: View){
        val skinViews= mutableListOf<SkinView>()
        SkinAttrSupport.addSkinViews(view,skinViews)
        skinViews.forEach { it.apply() }
    }

    private fun updatePluginInfo(pluginPath: String?, pluginPkg: String?, pluginSuffix: String?){
        SkinPrefsHelper.putPluginPath(mContext,pluginPath)
        SkinPrefsHelper.putPluginPkg(mContext,pluginPkg)
        SkinPrefsHelper.putPluginSuffix(mContext,pluginSuffix)

        mSuffix = pluginSuffix
    }

    /**
     * 清除皮肤，恢复默认
     */
    fun clearPlugin(){
        clearPluginInfo()
        notifyChangeListeners()
    }

    private fun clearPluginInfo() {
        mIsUsePlugin = false
        mSuffix = ""
        SkinPrefsHelper.clear(mContext)
    }

    private fun notifyChangeListeners(){
        activities.forEach { apply(it) }
    }

    private fun apply(activity: Activity){
        SkinAttrSupport.getSkinViews(activity).forEach { it.apply() }
    }

    fun register(activity: Activity){
        activities.add(activity)
        //1.会在UI线程中执行
        activity.findViewById<View>(android.R.id.content).post { apply(activity) }
    }

    fun unregister(activity: Activity){
        activities.remove(activity)
    }
}