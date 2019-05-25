package com.ycr.kernel.http

/**
 * 下载进度监听
 * created by yuchengren on 2019/5/16
 */
interface IDownloadProgress {
    /**
     * @param finishLength 已经完成的内容
     * @param totalLength 总长度
     */
    fun onProgress(finishLength: Long,totalLength: Long)
}