package com.ycr.kernel.http.okhttp

import com.ycr.kernel.http.*
import com.ycr.kernel.json.parse.IJsonParser
import okhttp3.*
import java.io.File

/**
 * Created by yuchengren on 2018/12/13.
 */
object OkHttpScheduler: HttpScheduler() {
    const val MEDIA_TYPE_JSON = "application/json; charset=utf-8"
    const val MEDIA_TYPE_PNG = "image/png"
    const val MEDIA_TYPE_BYTE_ARRAY= "application/octet-stream; charset=utf-8"

    private lateinit var jsonParser: IJsonParser
    private lateinit var okHttpClient: OkHttpClient

    fun doInit(jsonParser:IJsonParser ,okHttpClient: OkHttpClient =  OkHttpClient()){
        this.jsonParser = jsonParser
        this.okHttpClient = okHttpClient
    }

    override fun newCall(request: IRequest): ICall {
        val api = request.api()
        val urlBuilder = StringBuilder(api.url())
        val params = request.params()

        val requestBuilder = Request.Builder()
        when(api.requestMethod()){
            RequestMethod.GET ->{
                if(urlBuilder.last() != HttpConstants.QMARK){
                    urlBuilder.append(HttpConstants.QMARK)
                }else{
                    urlBuilder.append(HttpConstants.AND)
                }
                if(params != null){
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
                        params?.forEach { key, value ->
                            formBodyBuilder.add(key, value?.toString() ?: HttpConstants.EMPTY)
                        }
                        formBodyBuilder.build()
                    }
                    ParamType.JSON ->{
                        var json: String = jsonParser.toJson(params)?: HttpConstants.EMPTY
                        RequestBody.create(MediaType.parse(MEDIA_TYPE_JSON),json)
                    }
                    ParamType.FILE ->{
                        val bodyBuilder = MultipartBody.Builder()
                        params?.forEach { key, value ->
                            when(value){
                                is File -> bodyBuilder.addFormDataPart(key,value.name, RequestBody.create(
                                        MediaType.parse(MEDIA_TYPE_PNG),value))
                                is ByteArray -> bodyBuilder.addFormDataPart(key,key, RequestBody.create(
                                        MediaType.parse(MEDIA_TYPE_BYTE_ARRAY),value))
                                else -> bodyBuilder.addFormDataPart(key,value?.toString()?:HttpConstants.EMPTY)
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
        val okHttpRequest = requestBuilder.url(urlBuilder.toString()).build()
        val okHttpCall = okHttpClient.newCall(okHttpRequest)
        return OkHttpCall(okHttpCall)
    }
}