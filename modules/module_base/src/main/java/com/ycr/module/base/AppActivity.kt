package com.ycr.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.ycr.module.framework.mvvm.BaseViewModel
import com.ycr.module.framework.mvvm.IUIChangeView

/**
 * created by yuchengren on 2019-05-31
 */
abstract class AppActivity<B: ViewDataBinding>: BaseActivity() {

    protected lateinit var viewModelProvider: ViewModelProvider
    protected val viewModelMap = mutableMapOf<Int,BaseViewModel>()

    protected lateinit var viewDataBinding: B

    fun <VM: BaseViewModel> getViewModel(cls: Class<VM>): VM{
        return viewModelProvider.get(cls)
    }

    fun <VM: BaseViewModel> setViewModel(variableId: Int,cls: Class<VM>){
        val viewModel = getViewModel(cls)
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        //ViewModel与View的契约事件回调
        registerUIChangeLiveDataObserver(viewModel)

        viewModelMap[variableId] = viewModel
        viewDataBinding.setVariable(variableId,viewModel)
    }

    override fun createView(savedInstanceState: Bundle?) {
        beforeBindView()
        viewDataBinding = DataBindingUtil.setContentView(this,rootLayoutResId)
        viewModelProvider = ViewModelProviders.of(this)
        bindView(viewDataBinding.root)
        afterBindView(viewDataBinding.root,savedInstanceState)
    }

    private fun registerUIChangeLiveDataObserver(viewModel: BaseViewModel){
        viewModel.getUC().getShowDialogLiveData().observe(this, Observer{
            showLoading(it,true)
        })

        viewModel.getUC().getDismissDialogLiveData().observe(this, Observer{
            dismissLoading(true)
        })

        viewModel.getUC().getStartActivityLiveData().observe(this, Observer{
            val cls = (it?.get(IUIChangeView.CLAZZ) as? Class<*>)?:return@Observer
            val bundle = it?.get(IUIChangeView.BUNDLE) as? Bundle
            startActivity(cls,bundle)
        })

        viewModel.getUC().getFinishLiveData().observe(this, Observer{
            this@AppActivity.finish()
        })

        viewModel.getUC().getOnBackPressedLiveData().observe(this, Observer{
            this@AppActivity.onBackPressed()
        })

    }

    fun startActivity(cls: Class<*>,bundle: Bundle?){
        startActivity(Intent(this,cls).apply {
            if(bundle != null){
                putExtras(bundle)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelMap.forEach {
            it.value.run {
                this@AppActivity.lifecycle.removeObserver(this)
                unregisterRxBus()
            }
        }
        viewDataBinding.unbind()
    }

}