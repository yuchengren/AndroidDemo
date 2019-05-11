package com.ycr.lib.ui.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.support.v7.app.AppCompatDialog
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.io.Serializable
import java.lang.IllegalArgumentException

/**
 * created by yuchengren on 2019/5/11
 */
class BaseDialog constructor(@NonNull context: Context,
                             contentView: View?,
                             contextViewResId: Int,
                             gravity: Int,
                             animationStyleId: Int,
                             dimAmount: Float,
                             width: Int,
                             height: Int,
                             cancelable: Boolean,
                             cancelOnTouchOutside: Boolean): AppCompatDialog(context) {

    constructor(@NonNull context: Context,@NonNull builder: BaseBuilder): this(context,
            builder.contentView,
            builder.animationStyleId,
            builder.gravity,
            builder.animationStyleId,
            builder.dimAmount,
            builder.width,
            builder.height,
            builder.cancelable,
            builder.cancelOnTouchOutside)

    init {
        window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            if(contentView != null){
                setContentView(contentView)
            }else{
                if(contextViewResId > 0){
                    setContentView(contextViewResId)
                }
            }

            attributes = attributes.also {
                if(width != Int.MIN_VALUE){
                    it.width = width
                }
                if(height != Int.MIN_VALUE){
                    it.height = height
                }
                it.gravity = gravity
                if(animationStyleId > 0){
                    it.windowAnimations = animationStyleId
                }
                if(dimAmount < 0f){
                    it.flags = it.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
                }else{
                    it.flags = it.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
                }
            }
            //禁用系统默认风格的Dialog背景
            setBackgroundDrawable(ColorDrawable(0x00000000))
            setCancelable(cancelable)
            setCanceledOnTouchOutside(cancelOnTouchOutside)
        }

    }

    open class BaseBuilder: Serializable{
        @Transient var contentView: View? = null

        @LayoutRes var contextViewResId: Int = -1

        var gravity = Gravity.CENTER

        @StyleRes var animationStyleId: Int = -1

        var dimAmount: Float = 0.6f //Dialog蒙层的暗度

        var width: Int = WindowManager.LayoutParams.MATCH_PARENT

        var height: Int = WindowManager.LayoutParams.WRAP_CONTENT

        var cancelable = true

        var cancelOnTouchOutside = true


        fun contentView(contentView : View?): BaseBuilder{
            this.contentView = contentView
            return this 
        }

        fun contextViewResId(contextViewResId: Int): BaseBuilder{
            this.contextViewResId = contextViewResId
            return this
        }

        fun gravity(gravity: Int): BaseBuilder{
            this.gravity = gravity
            return this
        }

        fun animationStyleId(animationStyleId: Int): BaseBuilder{
            this.animationStyleId = animationStyleId
            return this
        }

        fun width(width: Int): BaseBuilder{
            this.width = width
            return this
        }

        fun height(height: Int): BaseBuilder{
            this.height = height
            return this
        }

        fun cancelable(cancelable: Boolean): BaseBuilder{
            this.cancelable = cancelable
            return this
        }

        fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): BaseBuilder{
            this.cancelOnTouchOutside = cancelOnTouchOutside
            return this
        }

        fun build(@NonNull context: Context): BaseDialog{
            if(context == null){
                throw IllegalArgumentException("BaseBuilder build(),context is null")
            }
            return BaseDialog(context,this)
        }

    }
}