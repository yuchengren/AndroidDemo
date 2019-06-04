package com.ycr.module.framework.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import com.ycr.kernel.task.AsyncTaskInstance
import com.ycr.kernel.task.IGroup
import com.ycr.kernel.task.TaskScheduler
import com.ycr.kernel.union.task.CommonTask
import com.ycr.kernel.union.task.TaskHelper
import com.ycr.kernel.util.getTaskNameFromTrace
import com.ycr.module.framework.mvvm.event.SingleLiveData

/**
 * created by yuchengren on 2019/5/28
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel, IUIChangeView, IGroup {

    protected val TAG = javaClass.name

    protected val defaultTaskName: String
        get() = Thread.currentThread().getTaskNameFromTrace(5)

    private lateinit var uc: UIChangeLiveData

    override fun groupName(): String? {
        return javaClass.name + hashCode()
    }

    /**
     * 释放资源
     */
    override fun onCleared() {
        super.onCleared()
        TaskScheduler.cancelGroup(groupName())
    }


    fun <T> submitTask(task: CommonTask<T>): AsyncTaskInstance<T> {
        return TaskHelper.submitTask(groupName(), defaultTaskName, task, task)
    }

    fun <T> submitTask(groupName: String, taskName: String, task: CommonTask<T>): AsyncTaskInstance<T> {
        return TaskHelper.submitTask(groupName, taskName, task, task)
    }

    fun <T> submitTask(taskName: String, task: CommonTask<T>): AsyncTaskInstance<T> {
        return TaskHelper.submitTask(groupName(), taskName, task, task)
    }

    fun <T> submitTaskDefaultGroup(task: CommonTask<T>): AsyncTaskInstance<T> {
        return TaskHelper.submitTask(IGroup.GROUP_NAME_DEFAULT, defaultTaskName, task, task)
    }


    fun getUC(): UIChangeLiveData {
        if (!this::uc.isInitialized) {
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
        getUC().getDismissDialogLiveData().call()
    }

    override fun startActivity(cls: Class<*>) {
        startActivity(cls, null)
    }

    override fun startActivity(cls: Class<*>, bundle: Bundle?) {
        getUC().getStartActivityLiveData().postValue(
                mutableMapOf(Pair(IUIChangeView.CLAZZ, cls), Pair(IUIChangeView.BUNDLE, bundle)))
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
    override fun onResume() {
        TaskScheduler.onResume(groupName())
    }

    override fun onPause() {
        TaskScheduler.onPause(groupName())
    }

    override fun registerRxBus() {}
    override fun unregisterRxBus() {}

    class UIChangeLiveData {
        private lateinit var showDialogLiveData: SingleLiveData<String?>
        private lateinit var dismissDialogLiveData: SingleLiveData<Void>
        private lateinit var startActivityLiveData: SingleLiveData<Map<String, Any?>>
        private lateinit var startContainerActivityLiveData: SingleLiveData<Map<String, Any?>>
        private lateinit var finishLiveData: SingleLiveData<Void>
        private lateinit var onBackPressedLiveData: SingleLiveData<Void>

        fun getShowDialogLiveData(): SingleLiveData<String?> {
            if (!this::showDialogLiveData.isInitialized) {
                showDialogLiveData = SingleLiveData()
            }
            return showDialogLiveData
        }

        fun getDismissDialogLiveData(): SingleLiveData<Void> {
            if (!this::dismissDialogLiveData.isInitialized) {
                dismissDialogLiveData = SingleLiveData()
            }
            return dismissDialogLiveData
        }

        fun getStartActivityLiveData(): SingleLiveData<Map<String, Any?>> {
            if (!this::startActivityLiveData.isInitialized) {
                startActivityLiveData = SingleLiveData()
            }
            return startActivityLiveData
        }

        fun getStartContainerActivityLiveData(): SingleLiveData<Map<String, Any?>> {
            if (!this::startContainerActivityLiveData.isInitialized) {
                startContainerActivityLiveData = SingleLiveData()
            }
            return startContainerActivityLiveData
        }

        fun getFinishLiveData(): SingleLiveData<Void> {
            if (!this::finishLiveData.isInitialized) {
                finishLiveData = SingleLiveData()
            }
            return finishLiveData
        }

        fun getOnBackPressedLiveData(): SingleLiveData<Void> {
            if (!this::onBackPressedLiveData.isInitialized) {
                onBackPressedLiveData = SingleLiveData()
            }
            return onBackPressedLiveData
        }
    }
}