package com.yuchengren.mvp.app.ui.view

import android.graphics.Path

/**
 * Created by yuchengren on 2018/11/2.
 */
class SVGPath(var path: Path = Path()){

    var pathData: StringBuilder = StringBuilder()

    fun lineTo(x: Float,y: Float){
        pathData.append("L$x$SIGN_COLON$y")
        path.lineTo(x,y)
    }

    fun moveTo(x: Float,y: Float){
        pathData.append("M$x$SIGN_COLON$y")
        path.moveTo(x,y)
    }

    companion object {
        const val SIGN_COLON =","
        const val SIGN_BLANK = " "
    }


}