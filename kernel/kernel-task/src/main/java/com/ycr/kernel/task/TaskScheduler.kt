package com.ycr.kernel.task

import android.os.Process
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit

/**
 * Created by yuchengren on 2018/9/13.
 */
object TaskScheduler {
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = CPU_COUNT * 2 + 1 // 核心线程数
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 16 + 1 //最大线程数
    private val QUEUE_SIZE = CORE_POOL_SIZE + 1 //线程队列大小
    private const val KEEP_ALIVE_TIME = 1L //线程存活时间
    private val executor: TaskThreadPoolExecutor
    init {
        val queue:PriorityBlockingQueue<Runnable> = PriorityBlockingQueue(QUEUE_SIZE)
        val threadFactory = PriorityThreadFactory(
                "priority-thread-pool-",Process.THREAD_PRIORITY_BACKGROUND)
        executor = TaskThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS
                ,queue,threadFactory,ExecutorInterceptor())
    }

    private class ExecutorInterceptor: TaskThreadPoolExecutor.Interceptor{

        override fun beforeExecute(t: Thread?, r: Runnable?) {

        }
        override fun afterExecute(r: Runnable?, t: Throwable?) {
        }


    }

    fun <R> submit(task: AbstractTask<R>){

    }


}