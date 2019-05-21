package com.ycr.kernel.union.mvp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ycr.kernel.mvp.MvpFragment
import com.ycr.kernel.task.IGroup
import com.ycr.kernel.task.TaskScheduler
import com.ycr.kernel.union.*
import com.ycr.kernel.union.constants.ReferenceMode
import com.ycr.kernel.union.mvp.view.IContentView
import com.ycr.kernel.union.mvp.view.IDefineView
import com.ycr.kernel.union.mvp.view.ViewCreateHelper

import java.lang.ref.Reference
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Created by yuchengren on 2018/12/10.
 */
abstract class UnionFragment : MvpFragment(), IGroup, IDefineView, IContentView {

    private var rootViewReference: Reference<View>? = null
    private var rootView: View? = null

    private val isInitOnce: Boolean
        get() = true

    private val viewReferenceMode: Int
        @ReferenceMode get() = ReferenceMode.SOFT

    override fun groupName(): String? {
        return javaClass.name + hashCode()
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = getRootView()
        if (rootView == null || !isInitOnce) {
            rootView = ViewCreateHelper.createView(context, this, savedInstanceState)
        }
        return rootView!!
    }

    private fun getRootView(): View? {
        if (rootView != null) {
            return rootView
        }
        if (rootViewReference != null) {
            return rootViewReference?.get()
        }
        UnionLog.e(javaClass.name, "getRootView is null!")
        return null
    }

    @SuppressLint("SwitchIntDef")
    override fun setContentView(view: View) {
        when (viewReferenceMode) {
            ReferenceMode.STRONG -> rootView = view
            ReferenceMode.SOFT -> rootViewReference = SoftReference(view)
            ReferenceMode.WEAK -> rootViewReference = WeakReference(view)
            else -> rootViewReference = SoftReference(view)
        }
    }

    override fun onResume() {
        super.onResume()
        TaskScheduler.onResume(groupName())
    }

    override fun onPause() {
        super.onPause()
        TaskScheduler.onPause(groupName())
    }

    override fun onDestroy() {
        val groupName = groupName()
        TaskScheduler.cancelGroup(groupName)
        super.onDestroy()
    }

    /**
     * kotlin view id 直接调用控件，实际为getView().findViewById(R.id.xxx)
     */
    override fun getView(): View? {
        return super.getView()?: getRootView()
    }
}
