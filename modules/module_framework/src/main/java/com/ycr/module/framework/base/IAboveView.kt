package com.ycr.module.framework.base

/**
 * created by yuchengren on 2019/4/28
 */
interface IAboveView {

    /**
     * 显示loading
     */
    fun showLoading(msg: String?, isEmpty: Boolean)

    /**
     * 隐藏loading
     */
    fun dismissLoading(isSuccess: Boolean)
}