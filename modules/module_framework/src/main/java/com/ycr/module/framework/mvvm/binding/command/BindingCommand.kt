package com.ycr.module.framework.mvvm.binding.command

/**
 * created by yuchengren on 2019-06-04
 */
class BindingCommand(private val execute: VoidAction?,private val canExecute: OutAction<Boolean>? = null) {

    fun execute(){
        if(canExecute()){
            execute?.call()
        }
    }

    private fun canExecute(): Boolean{
        return canExecute?.call()?:true
    }
}