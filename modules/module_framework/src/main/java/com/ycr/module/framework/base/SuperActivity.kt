package com.ycr.module.framework.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.view.LayoutInflater
import android.view.View

import com.ycr.kernel.http.IResult
import com.ycr.kernel.task.AsyncTaskInstance
import com.ycr.kernel.task.IGroup
import com.ycr.kernel.union.mvp.UnionActivity
import com.ycr.kernel.union.task.TaskHelper
import com.ycr.kernel.util.*
import com.ycr.module.framework.helper.InjectHelper
import com.ycr.module.framework.task.ApiTask
import com.ycr.module.framework.view.ILayoutInflaterFactoryCreator

import butterknife.ButterKnife

/**
 * Created by yuchengren on 2018/12/10.
 */
abstract class SuperActivity : UnionActivity() {

    protected val isSupportButterKnife: Boolean
        get() = false

    protected val isSupportDagger: Boolean
        get() = false

    val defaultTaskName: String
        get() = Thread.currentThread().getTaskNameFromTrace(5)


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

    protected fun doInject() {
        if (isSupportDagger) {

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
