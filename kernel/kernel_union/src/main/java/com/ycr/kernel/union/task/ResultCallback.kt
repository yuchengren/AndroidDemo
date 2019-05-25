package com.ycr.kernel.union.task

import android.support.annotation.CallSuper
import com.ycr.kernel.http.IResult
import com.ycr.kernel.task.AbstractTask
import com.ycr.kernel.task.ITaskCallback
import com.ycr.kernel.union.UnionLog

/**
 * Created by yuchengren on 2018/12/7.
 */
abstract class ResultCallback<T>: ITaskCallback<IResult<T>>, IResultCallback<IResult<T>> {

    override fun onBeforeCall(task: AbstractTask<IResult<T>>) {

    }

    override fun onAfterCall() {
    }

    final override fun onCompleted(data: IResult<T>) {
        if(data.isSuccess()){
            doSuccess(data)
        }else{
            doFailure(data)
        }
        doFinishCall(data)
    }

    private fun doSuccess(data: IResult<T>) {
        try {
            onSuccess(data)
        }catch (tr: Throwable){
            printException("doSuccess",tr)
        }
    }

    protected open fun doFailure(data: IResult<T>) {
        try {
            onFailure(data)
        }catch (tr: Throwable){
            printException("doFailure",tr)
        }
    }

    @CallSuper
    override fun onError(tr: Throwable) {
        val result: IResult<T> = parseExceptionToResult(tr)
        doFailure(result)
        doFinishCall(result)
    }

    private fun doFinishCall(result: IResult<T>) {
        try {
            onFinishCall(result)
        }catch (tr: Throwable){
            printException("doFinishCall",tr)
        }
    }

    open fun onFinishCall(result: IResult<T>) {

    }

    abstract fun parseExceptionToResult(tr: Throwable): IResult<T>

    @CallSuper
    override fun onCancelled() {
        val result: IResult<T> = parseCancelledToResult()
        doFailure(result)
        doFinishCall(result)
    }

    abstract fun parseCancelledToResult(): IResult<T>

    override fun onSuccess(result: IResult<T>) {
    }

    override fun onFailure(result: IResult<T>) {
    }

    protected fun printException(period: String,tr: Throwable){
        UnionLog.e(tr," exception occur at $period ")
    }


}