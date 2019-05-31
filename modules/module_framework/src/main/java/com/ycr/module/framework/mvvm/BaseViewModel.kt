package com.ycr.module.framework.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import com.ycr.kernel.union.helper.ContextHelper

/**
 * created by yuchengren on 2019/5/28
 */
abstract class BaseViewModel: AndroidViewModel,IBaseViewModel,IUIChangeView {

    private lateinit var uc: UIChangeLiveData

    constructor(): super(ContextHelper.getApplication())

    constructor(application: Application): super(application)

    fun getUC(): UIChangeLiveData{
        if(!this::uc.isInitialized){
            uc = UIChangeLiveData()
        }
        return uc
    }

    override fun showLoading() {
        showLoading("")
    }

    override fun showLoading(msg: String?) {
        getUC().getShowDialogLiveData().postValue(msg)
    }

    override fun dismissLoading() {
        getUC().getShowDialogLiveData().call()
    }

    override fun startActivity(cls: Class<*>) {
        startActivity(cls,null)
    }

    override fun startActivity(cls: Class<*>, bundle: Bundle?) {
        getUC().getStartActivityLiveData().postValue(
                mutableMapOf(Pair(IUIChangeView.CLAZZ,cls),Pair(IUIChangeView.BUNDLE,bundle)))
    }

    override fun finish() {
        getUC().getFinishLiveData().call()
    }

    override fun onBackPressed() {
        getUC().getOnBackPressedLiveData().call()
    }


    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {}
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}
    override fun registerRxBus() {}
    override fun unregisterRxBus() {}

    class UIChangeLiveData{
        private lateinit var showDialogLiveData: SingleLiveData<String?>
        private lateinit var dismissDialogLiveData: SingleLiveData<Void>
        private lateinit var startActivityLiveData: SingleLiveData<Map<String, Any?>>
        private lateinit var startContainerActivityLiveData: SingleLiveData<Map<String, Any?>>
        private lateinit var finishLiveData: SingleLiveData<Void>
        private lateinit var onBackPressedLiveData: SingleLiveData<Void>

        fun getShowDialogLiveData(): SingleLiveData<String?> {
            if(!this::showDialogLiveData.isInitialized){
                showDialogLiveData = SingleLiveData()
            }
            return showDialogLiveData
        }

        fun getDismissDialogLiveData(): SingleLiveData<Void> {
            if(!this::dismissDialogLiveData.isInitialized){
                dismissDialogLiveData = SingleLiveData()
            }
            return dismissDialogLiveData
        }

        fun getStartActivityLiveData(): SingleLiveData<Map<String, Any?>> {
            if(!this::startActivityLiveData.isInitialized){
                startActivityLiveData = SingleLiveData()
            }
            return startActivityLiveData
        }

        fun getStartContainerActivityLiveData(): SingleLiveData<Map<String, Any?>> {
            if(!this::startContainerActivityLiveData.isInitialized){
                startContainerActivityLiveData = SingleLiveData()
            }
            return startContainerActivityLiveData
        }

        fun getFinishLiveData(): SingleLiveData<Void> {
            if(!this::finishLiveData.isInitialized){
                finishLiveData = SingleLiveData()
            }
            return finishLiveData
        }

        fun getOnBackPressedLiveData(): SingleLiveData<Void> {
            if(!this::onBackPressedLiveData.isInitialized){
                onBackPressedLiveData = SingleLiveData()
            }
            return onBackPressedLiveData
        }
    }
}