package com.ycr.kernel.http.okhttp

import com.ycr.kernel.http.*
import com.ycr.kernel.json.parse.IJsonParser
import okhttp3.*
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLConnection
import java.net.URLEncoder

/**
 * Created by yuchengren on 2018/12/13.
 */
object OkHttpScheduler: HttpScheduler() {
    private const val MEDIA_TYPE_JSON = "application/json; charset=utf-8"
    private const val MEDIA_TYPE_PNG = "image/png"
    private const val MEDIA_TYPE_BYTE_ARRAY= "application/octet-stream; charset=utf-8"

    private lateinit var jsonParser: IJsonParser
    private lateinit var okHttpClient: OkHttpClient

    fun doInit(jsonParser:IJsonParser ,okHttpClient: OkHttpClient =  OkHttpClient()): OkHttpScheduler{
        this.jsonParser = jsonParser
        this.okHttpClient = okHttpClient
        return this
    }

    override fun newCall(request: IRequest): ICall {
        val api = request.api()
        val params = request.params()
        val urlBuilder = StringBuilder(api.url())

        val requestBuilder = Request.Builder()
        when(api.requestMethod()){
            RequestMethod.GET ->{
                if(params != null){
                    if(urlBuilder.last() != HttpConstants.QMARK){
                        urlBuilder.append(HttpConstants.QMARK)
                    }
                    val iterator = params.iterator()
                    while (iterator.hasNext()){
                        val next = iterator.next()
                        urlBuilder.append(next.key + HttpConstants.EQUAL + next.value)
                        if(iterator.hasNext()){
                            urlBuilder.append(HttpConstants.AND)
                        }
                    }
                }
                requestBuilder.get()
            }

            RequestMethod.POST ->{
                val requestBody: RequestBody =
                        when(api.paramType()){
                            ParamType.NORMAL ->{
                                val formBodyBuilder = FormBody.Builder()
                                params?.forEach {

                                    formBodyBuilder.add(it.key, it.value?.toString() ?: HttpConstants.EMPTY)
                                }

                                formBodyBuilder.build()
                            }
                            ParamType.JSON ->{
                                val json: String = jsonParser.toJson(params)?: HttpConstants.EMPTY
                                RequestBody.create(MediaType.parse(MEDIA_TYPE_JSON),json)
                            }
                            ParamType.FILE ->{
                                val bodyBuilder = MultipartBody.Builder()
                                        .setType(MultipartBody.FORM) // 设置type为"multipart/form-data"，不然无法上传参数
                                params?.forEach {
                                    val value = it.value
                                    val key = it.key
                                    when(value){
                                        //文件类型
                                        is File -> bodyBuilder.addFormDataPart(key,value.name, RequestBody.create(
                                                MediaType.parse(guessMimeType(value.name)),value))
                                        is ByteArray -> bodyBuilder.addFormDataPart(key,key, RequestBody.create(
                                                MediaType.parse(MEDIA_TYPE_BYTE_ARRAY),value))
                                        else -> bodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"$key\""),
                                                RequestBody.create(null, value.toString()))
                                    }
                                }
                                bodyBuilder.build()
                            }
                        }
                requestBuilder.post(requestBody)
            }
        }
        api.headers()?.let {
            requestBuilder.headers(Headers.of(it))
        }

        OkHttpModuleLog.d("HttpRequest:${api.requestMethod()}\nurl=$urlBuilder,\nparams=${params?.toString()}")

        val okHttpRequest = requestBuilder.url(urlBuilder.toString()).build()
        val okHttpCall = okHttpClient.newCall(okHttpRequest)
        return OkHttpCall(okHttpCall)
    }

    private fun guessMimeType(path: String): String {
        val fileNameMap = URLConnection.getFileNameMap()
        var contentTypeFor: String? = null
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream"
        }
        return contentTypeFor
    }
}