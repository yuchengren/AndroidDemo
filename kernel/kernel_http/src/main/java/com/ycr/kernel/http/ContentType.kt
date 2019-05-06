package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/13.
 */
enum class ContentType(val contentType: String) {
    APP_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APP_JSON("application/json"),
    APP_OCTET_STREAM("application/octet-stream"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    WILDCARD("*/*");
}