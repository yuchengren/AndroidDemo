package com.ycr.kernel.union.http

import android.content.Context
import com.ycr.kernel.http.*

import com.ycr.kernel.task.TaskInfo
import com.ycr.kernel.union.exception.NetworkNotConnectedException
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.union.helper.UnionContainer
import com.ycr.kernel.util.*
import com.ycr.kernel.util.ThreadLocalHelper

import com.ycr.kernel.task.TASK_INFO
import com.ycr.kernel.union.BuildConfig.logger
import java.io.File
import kotlin.random.Random

/**
 * created by yuchengren on 2019/4/23
 */
object HttpHelper {
    var httpScheduler = UnionContainer.httpScheduler

    @JvmStatic fun <T> execute(api: IApi, params: Any?): IResult<T> {
        if (!ContextHelper.getContext().isNetworkConnected()) {
            throw NetworkNotConnectedException()
        }
        val newCall = httpScheduler.newCall(api.newRequestBuilder().setParams(params).build())
        val taskInfo = ThreadLocalHelper.getThreadLocalInfo<TaskInfo>(TASK_INFO)
        val response = httpScheduler.execute(newCall, taskInfo?.groupName, taskInfo?.taskName)
        return httpScheduler.parse(api, response)
    }

    @JvmStatic fun cancelGroup(groupName: String) {
        httpScheduler.cancelGroup(groupName)
    }

    @JvmStatic fun download(file: File?, url: String, downProgress: IDownloadProgress): File?{
        if (!ContextHelper.getContext().isNetworkConnected()) {
            throw NetworkNotConnectedException()
        }
        if(file == null){
            return null
        }

        if(!file.exists()){
            try {
                file.mkdirs()
            }catch (e: Throwable){
                logger.e(e)
            }
        }
        if(!file.exists()){
            return null
        }

        val saveFile = if(file.isDirectory){
            val pre = "${System.currentTimeMillis()}_+ ${Random.nextLong()}"
            File.createTempFile(pre,".temp",file)
        }else file

        if(!file.canWrite()){
            return null
        }

        val newCall = httpScheduler.newCall(UnionApi.get(url).newRequestBuilder().build())
        httpScheduler.download(newCall,saveFile,downProgress)
        return saveFile
    }

}
