package com.ycr.lib.changeskin.callback

/**
 * Created by yuchengren on 2018/8/30.
 */
interface ISkinChangingCallback {
    companion object {
        val ADAPTER = SkinChangingAdapter()
    }

    fun onStart()

    fun onCompleted()

    fun onError(e: Exception)

}

class SkinChangingAdapter: ISkinChangingCallback{

    override fun onError(e: Exception) {}

    override fun onCompleted() {}

    override fun onStart() {}
}