package com.ycr.kernel.union

import android.content.Context

/**
 * created by yuchengren on 2019/5/7
 */
abstract class AbstractAppInit @JvmOverloads constructor(name: String?, var period: Int = PERIOF_APP_MAIN_THREAD,
                               var priority: Int = PRIORITY_MIDDLE): Comparable<AbstractAppInit> {

    private var initCount = 0


    open fun initOnce(): Boolean{
        return true
    }

    fun init(context: Context): Boolean{
        if(initCount == 0 || !initOnce()){
            if(doInit(context)){
                initCount++
                return true
            }
        }
        return false
    }

    abstract fun doInit(context: Context): Boolean

    override fun compareTo(other: AbstractAppInit): Int {
        val result = priority.compareTo(other.priority)
        return if(result == 0){
            result
        }else{
            hashCode().compareTo(other.hashCode())
        }
    }

    companion object{
        /**
         * 在app启动的阶段
         */
        const val PERIOF_APP_MAIN_THREAD = 0
        /**
         * 第一个Activity启动阶段
         */
        const val PERIOF_FIRST_ACTIVITY = 1
        /**
         * home启动的阶段
         */
        const val PERIOF_HOME_ACTIVITY = 2

        /**
         * 高优先级
         */
        const val PRIORITY_HIGH = 256
        /**
         * 中优先级
         */
        const val PRIORITY_MIDDLE = 32
        /**
         * 低优先级
         */
        const val PRIORITY_LOW = 1
    }
}