package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/13.
 */
class PriorityThreadFactory(namePrefix: String,private val priority: Int) : NamedThreadFactory(namePrefix){

    override fun newThread(name: String, r: Runnable): Thread {
        return PriorityThread(priority,name,r)
    }
}