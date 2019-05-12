package com.ycr.lib.ui.dialog

import android.content.Context
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.View

/**
 * created by yuchengren on 2019/5/11
 */
class MessageDialogFragement: DialogFragment() {

    class Builder: BaseDialog.BaseBuilder<Builder>(){
        var isSave: Boolean = false
        var title: CharSequence? = null
        var titleResId: Int = -1
        var contentText: CharSequence? = null
        var contentTextResId: Int = -1
        var contextTextGravity: Int = Gravity.START
        var contentTextAlignment: Int = View.TEXT_ALIGNMENT_GRAVITY
        var buttonTextResIds: IntArray? = null




    }
}