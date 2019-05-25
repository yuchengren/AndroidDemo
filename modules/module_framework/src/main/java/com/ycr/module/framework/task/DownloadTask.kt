package com.ycr.module.framework.task

import com.ycr.kernel.http.IDownloadProgress
import com.ycr.kernel.http.IResult
import com.ycr.kernel.union.http.HttpHelper
import com.ycr.kernel.union.task.SimpleResult
import java.io.File

/**
 * created by yuchengren on 2019/5/16
 */
open class DownloadTask(val file: File?, val url: String): ApiTask<File?>() {

    private val downloadProgress = object : IDownloadProgress {
        override fun onProgress(finishLength: Long, totalLength: Long) {
            if(totalLength == 0L){
                return
            }
            this@DownloadTask.postProgress((finishLength.toFloat() / totalLength * 100).toInt())
        }
    }

    override fun doInBackground(): IResult<File?> {
        val saveFile = HttpHelper.download(file, url, downloadProgress)
        return if(saveFile == null){
            SimpleResult.fail("文件${file?.path.toString()}不存在或没有读写权限！")
        }else{
            SimpleResult.success("","",saveFile)
        }
    }
}