package com.ycr.kernel.union.mvp

import android.os.Bundle

import com.ycr.kernel.mvp.MvpActivity
import com.ycr.kernel.task.IGroup
import com.ycr.kernel.task.TaskScheduler
import com.ycr.kernel.union.*
import com.ycr.kernel.union.http.HttpHelper
import com.ycr.kernel.union.mvp.view.IDefineView
import com.ycr.kernel.union.mvp.view.ViewCreateHelper
import com.ycr.kernel.util.KeyBoardUtils


/**
 * Created by yuchengren on 2018/12/10.
 */
abstract class UnionActivity : MvpActivity(), IGroup, IDefineView {

    override fun afterCreate(savedInstanceState: Bundle?) {
        super.afterCreate(savedInstanceState)
        createView(savedInstanceState)
    }

    open fun createView(savedInstanceState: Bundle?){
        ViewCreateHelper.createView(this, this, savedInstanceState)
    }

    override fun groupName(): String? {
        return javaClass.name + hashCode()
    }

    override fun onDestroy() {
        val groupName = groupName()
        TaskScheduler.cancelGroup(groupName)
        HttpHelper.cancelGroup(groupName!!)
        UnionLog.d("", "cancel group-%s at onDestroy", groupName)
        KeyBoardUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        TaskScheduler.onPause(groupName())
    }

    override fun onResume() {
        super.onResume()
        TaskScheduler.onResume(groupName())
    }

}
