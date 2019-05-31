package com.ycr.module.base

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ycr.module.framework.mvvm.BaseViewModel

/**
 * created by yuchengren on 2019-05-31
 */
abstract class AppFragment<B: ViewDataBinding>: BaseFragment() {

    protected lateinit var viewDataBinding: B

    fun <VM: BaseViewModel> getViewModel(clazz: Class<VM>): VM{
        return ViewModelProviders.of(this).get(clazz)
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        beforeBindView()
        viewDataBinding = DataBindingUtil.inflate(inflater,rootLayoutResId,container,false)
        bindView(viewDataBinding.root)
        afterBindView(viewDataBinding.root,savedInstanceState)
        return viewDataBinding.root
    }
}