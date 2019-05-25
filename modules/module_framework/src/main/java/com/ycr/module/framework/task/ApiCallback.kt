package com.ycr.module.framework.task

import com.ycr.kernel.http.IResult
import com.ycr.kernel.union.task.ResultCallback
import com.ycr.kernel.union.task.SimpleResult

/**
 * Created by yuchengren on 2018/12/10.
 */
abstract class ApiCallback<T>: ResultCallback<T>(){

    override fun parseExceptionToResult(tr: Throwable): IResult<T> {
        return SimpleResult.fail(ResultStatus.PARSE_EXCEPTION,tr)
    }

    override fun parseCancelledToResult(): IResult<T> {
        return SimpleResult.fail(ResultStatus.TASK_CANCELLED)
    }

    final override fun doFailure(data: IResult<T>) {
        showErrorScene(data)
        super.doFailure(data)
    }

    protected fun showErrorScene(data: IResult<T>) {

    }


}