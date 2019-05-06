package com.ycr.kernel.union.helper

import com.ycr.kernel.http.IHttpScheduler
import com.ycr.kernel.image.glide.IImageLoader
import com.ycr.kernel.json.parse.IJsonParser

/**
 * created by yuchengren on 2019/4/22
 */
object UnionContainer {
    lateinit var jsonParser: IJsonParser
    lateinit var httpScheduler: IHttpScheduler
    lateinit var imageLoader: IImageLoader
}
