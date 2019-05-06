package com.ycr.kernel.task

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by yuchengren on 2018/9/13.
 */
open class NamedThreadFactory(private var namePrefix:String): ThreadFactory {
    private val threadIndex: AtomicInteger = AtomicInteger(0)

    open fun newThread(name: String,r: Runnable): Thread{
        return Thread(r,name)
    }

    override fun newThread(r: Runnable): Thread {
        return newThread(namePrefix + threadIndex.getAndIncrement(),r)
    }
}