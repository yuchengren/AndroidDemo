package com.ycr.kernel.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by yuchengren on 2018/12/13.
 */
fun Context.isNetworkConnected(): Boolean{
    val networkInfo = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?)?.activeNetworkInfo?:return false
    return networkInfo.isConnected
}