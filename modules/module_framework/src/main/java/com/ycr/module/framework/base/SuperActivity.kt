package com.ycr.module.framework.base

import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.view.LayoutInflater
import android.view.View
import com.ycr.kernel.http.IResult
import com.ycr.kernel.task.AsyncTaskInstance
import com.ycr.kernel.task.IGroup
import com.ycr.kernel.union.mvp.UnionActivity
import com.ycr.kernel.union.task.TaskHelper
import com.ycr.kernel.util.SystemBarTintManager
import com.ycr.kernel.util.SystemBarUtils
import com.ycr.kernel.util.getTaskNameFromTrace
import com.ycr.module.framework.R
import com.ycr.module.framework.helper.InjectHelper
import com.ycr.module.framework.task.ApiTask

/**
 * Created by yuchengren on 2018/12/10.
 */
abstract class SuperActivity : UnionActivity() {

    val defaultTaskName: String
        get() = Thread.currentThread().getTaskNameFromTrace(5)

    /**
     * 是否需要沉浸式状态栏
     */
    protected var isNeedTint: Boolean = true
        set(value) {
            field = value
            if(isSupportTint()){
                getTintManager().isStatusBarTintEnabled = value
            }
        }

    // 沉浸式状态栏
    private lateinit var tintManager: SystemBarTintManager

    override fun beforeBindView() {

    }

    override fun bindView(rootView: View?) {
//        if(isSupportButterKnife){
//            ButterKnife.bind(this,rootView?:return);
//        }
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {

    }

    override fun beforeCreate(savedInstanceState: Bundle?) {
        super.beforeCreate(savedInstanceState)
        doInject()
    }

    protected open fun isSupportButterKnife(): Boolean{
        return false
    }

    protected open fun isSupportDagger(): Boolean{
        return false
    }

    protected fun doInject() {
        if (isSupportDagger()) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutInflaterFactoryCreator = InjectHelper.layoutInflaterFactoryCreator
        if (layoutInflaterFactoryCreator != null) {
            val factory2 = layoutInflaterFactoryCreator
                    .createLayoutInflaterFactory2(delegate)
            if (factory2 != null) {
                LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), factory2)
            }
        }
        super.onCreate(savedInstanceState)
        if(isSupportTint()){
            initTint()
        }
    }

    private fun getTintManager(): SystemBarTintManager {
        if(!this::tintManager.isInitialized){
            tintManager = SystemBarTintManager(this)
        }
        return tintManager
    }

    private fun initTint() {
        SystemBarUtils.setTranslucentStatus(this, true)
        //沉浸栏设置
        getTintManager().isStatusBarTintEnabled = isNeedTint
        getTintManager().setStatusBarTintResource(getTintColor())
    }

    protected open fun getTintColor(): Int {
        return R.color.bg_bright
    }

    /**
     * 是否支持沉浸式状态栏
     */
    protected open fun isSupportTint(): Boolean{
        return false
    }


    fun <T> submitTask(task: ApiTask<T>): AsyncTaskInstance<IResult<T>> {
        return TaskHelper.submitTask(groupName(), defaultTaskName, task, task)
    }

    fun <T> submitTask(groupName: String, taskName: String, task: ApiTask<T>): AsyncTaskInstance<IResult<T>> {
        return TaskHelper.submitTask(groupName, taskName, task, task)
    }

    fun <T> submitTask(taskName: String, task: ApiTask<T>): AsyncTaskInstance<IResult<T>> {
        return TaskHelper.submitTask(groupName(), taskName, task, task)
    }

    fun <T> submitTaskDefaultGroup(task: ApiTask<T>): AsyncTaskInstance<IResult<T>> {
        return TaskHelper.submitTask(IGroup.GROUP_NAME_DEFAULT, defaultTaskName, task, task)
    }
}
