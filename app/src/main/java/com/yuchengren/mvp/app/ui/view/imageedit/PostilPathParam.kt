package com.yuchengren.mvp.app.ui.view.imageedit

import android.support.annotation.Keep

/**
 * 轨迹入参
 * Created by yuchengren on 2018/11/20.
 */
@Keep
class PostilPathParam{
    var id: Long = 0L //轨迹详情id 更新时需要
    var labelCode: Int = -1 //标记线段编号，没关联传-1或不传
    var labelLocus: String = "" //标记线段的轨迹
    var commentType: String =  COMMENT_TYPE_TEXT//留言关联的资源类型，1语音、2文本
    var commentResource: String = "" //留言内容：留言关联的资源，oss地址或者文本
    var commentResourceTime: Int = 0 //音频留言时长


    companion object {
        const val COMMENT_TYPE_TEXT = "2" //批注类型 文本
        const val COMMENT_TYPE_SPEECH  = "1" // 批注类型 语音
    }
}