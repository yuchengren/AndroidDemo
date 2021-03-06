package com.ycr.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ycr.module.framework.mvvm.viewmodel.BaseViewModel
import com.ycr.module.framework.mvvm.viewmodel.IUIChangeView
import java.lang.reflect.ParameterizedType

/**
 * created by yuchengren on 2019-05-31
 */
abstract class MvvmFragment<B: ViewDataBinding>: BaseFragment() {

    protected lateinit var viewModelProvider: ViewModelProvider
    protected val viewModelMap = mutableMapOf<Int, BaseViewModel>()

    protected lateinit var viewDataBinding: B

    fun <VM: BaseViewModel> getViewModel(cls: Class<VM>): VM{
        return viewModelProvider.get(cls)
    }

    fun <VM: BaseViewModel> setViewModel(variableId: Int, cls: Class<VM>){
        if(!this::viewModelProvider.isInitialized){
            return
        }

        val viewModel = getViewModel(cls).apply {
            //让ViewModel拥有View的生命周期感应
            lifecycle.addObserver(this)
            //ViewModel与View的契约事件回调
            registerUIChangeLiveDataObserver(this)
            registerRxBus()
            viewModelMap[variableId] = this
        }
        viewDataBinding.setVariable(variableId,viewModel)
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val tClass =(javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as? Class<B>
        if(tClass == null || tClass == ViewDataBinding::class.java){
            return super.createView(inflater,container,savedInstanceState)
        }
        beforeBindView()
        viewDataBinding = DataBindingUtil.inflate(inflater,rootLayoutResId,container,false)
        viewModelProvider = ViewModelProviders.of(this)
        bindView(viewDataBinding.root)
        afterBindView(viewDataBinding.root,savedInstanceState)
        return viewDataBinding.root
    }


    private fun registerUIChangeLiveDataObserver(viewModel: BaseViewModel){
        viewModel.getUC().getShowDialogLiveData().observe(this, Observer{
            (activity as? MvvmActivity<ViewDataBinding>)?.showLoading(it,true)
        })

        viewModel.getUC().getDismissDialogLiveData().observe(this, Observer{
            (activity as? MvvmActivity<ViewDataBinding>)?.dismissLoading(true)
        })

        viewModel.getUC().getStartActivityLiveData().observe(this, Observer{
            val cls = (it?.get(IUIChangeView.CLAZZ) as? Class<*>)?:return@Observer
            val bundle = it[IUIChangeView.BUNDLE] as? Bundle
            startActivity(cls,bundle)
        })

        viewModel.getUC().getFinishLiveData().observe(this, Observer{
            (activity as? MvvmActivity<ViewDataBinding>)?.finish()
        })
    }

    fun startActivity(cls: Class<*>,bundle: Bundle?){
        startActivity(Intent(context,cls).apply {
            if(bundle != null){
                putExtras(bundle)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelMap.forEach {
            it.value.run {
                lifecycle.removeObserver(this)
                unregisterRxBus()
            }
        }
        viewModelMap.clear()
        if(this::viewModelProvider.isInitialized){
            viewDataBinding.unbind()
        }
    }
}