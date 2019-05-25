package com.ycr.kernel.http

import com.ycr.kernel.http.BuildConfig.logger
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by yuchengren on 2018/12/14.
 */
abstract class HttpScheduler: IHttpScheduler {
    private val callGroup = ConcurrentHashMap<String,MutableMap<String,ICall>>()

    override fun execute(call: ICall, groupName: String?, taskName: String?): IResponse? {
        if(groupName == null || taskName == null){
            return call.execute()
        }
        var group = callGroup[groupName]
        if(group == null){
            group = mutableMapOf()
            callGroup[groupName] = group
        }
        group[taskName] = call
        val response = call.execute()
        group.remove(taskName)
        return response
    }

    override fun <T> parse(api: IApi, response: IResponse?): IResult<T> {
        val result = api.resultParser().parse<T>(api.resultType(), response, api)
        return result
    }

    override fun cancelGroup(groupName: String?){
        val group = callGroup[groupName]
        group?.run {
            for((_,call) in entries){
                call.cancel()
            }
            callGroup.remove(groupName)
        }
    }

    override fun download(call: ICall, saveFile: File, downProgress: IDownloadProgress?) {
        var fos: FileOutputStream? = null
        var inputStream: InputStream? = null
        try {
            val response = call.execute()?:return
            inputStream = response.inputStream?:return
            fos = FileOutputStream(saveFile)

            val buffer = ByteArray(2048)
            var len: Int
            val totalLength = response.totalLength
            var finishLength = 0L
            while (inputStream.read(buffer).apply { len = this } > 0){
                fos.write(buffer,0,len)
                fos.flush()
                finishLength += len.toLong()
                if(totalLength > 0){
                    downProgress?.onProgress(finishLength,totalLength)
                }
            }
        }catch (e: Throwable){
            logger.e(e)
            throw e
        }finally {
            try {
                inputStream?.close()
            }catch (e: IOException){
                logger.e(e)
            }
            try {
                fos?.close()
            }catch (e: IOException){
                logger.e(e)
            }
        }
    }
}