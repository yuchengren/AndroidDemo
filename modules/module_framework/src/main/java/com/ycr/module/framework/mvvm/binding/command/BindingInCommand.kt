package com.ycr.module.framework.mvvm.binding.command

/**
 * created by yuchengren on 2019-06-04
 */
class BindingInCommand<T>(private val execute: InAction<T>?, private val canExecute: OutAction<Boolean>? = null) {

    fun execute(t: T){
        if(canExecute()){
            execute?.call(t)
        }
    }

    private fun canExecute(): Boolean{
        return canExecute?.call()?:true
    }
}