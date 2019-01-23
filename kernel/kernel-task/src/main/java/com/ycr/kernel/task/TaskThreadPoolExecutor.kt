package com.ycr.kernel.task

import java.util.concurrent.*

/**
 * Created by yuchengren on 2018/9/6.
 */
class TaskThreadPoolExecutor(corePoolSize: Int,
                             maximumPoolSize: Int,
                             keepAliveTime: Long,
                             unit: TimeUnit?,
                             workQueue: BlockingQueue<Runnable>?,
                             threadFactory: ThreadFactory?,
                             private val interceptor: Interceptor?) : ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory){
    override fun beforeExecute(t: Thread?, r: Runnable?) {
        interceptor?.beforeExecute(t,r)
        super.beforeExecute(t, r)
    }

    override fun afterExecute(r: Runnable?, t: Throwable?) {
        super.afterExecute(r, t)
        interceptor?.afterExecute(r,t)
    }

    override fun <T> newTaskFor(callable: Callable<T>?): RunnableFuture<T>? {
        super.newTaskFor(callable)
        return callable as? RunnableFuture<T>
    }

    override fun <T> newTaskFor(runnable: Runnable?, value: T): RunnableFuture<T>? {
        return runnable as? RunnableFuture<T>
    }

    interface Interceptor{
        fun beforeExecute(t: Thread?, r: Runnable?)
        fun afterExecute(r: Runnable?, t: Throwable?)
    }
}

