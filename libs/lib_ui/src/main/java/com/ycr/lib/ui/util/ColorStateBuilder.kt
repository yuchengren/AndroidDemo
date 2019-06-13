package com.ycr.lib.ui.util

import android.content.res.ColorStateList
import android.widget.TextView

/**
 * created by yuchengren on 2019-06-13
 */
class ColorStateBuilder {
    val state_disabled = intArrayOf(-android.R.attr.state_enabled)
    val state_pressed = intArrayOf(android.R.attr.state_pressed)
    val state_selected = intArrayOf(android.R.attr.state_selected)

    val state_none = intArrayOf()

    val states  = arrayOf(state_disabled,state_pressed,state_selected,state_none)

    var defaultTextColor: Int = 0
    var pressedTextColor: Int = 0
    var disabledTextColor: Int = 0
    var selectedTextColor: Int = 0


    private fun getTextColorStateList(): ColorStateList {
        val defaultTextColor = defaultTextColor
        val disabledTextColor = if(disabledTextColor != 0) disabledTextColor else defaultTextColor
        val pressedTextColor = if(pressedTextColor != 0) pressedTextColor else defaultTextColor
        val selectedTextColor = if(selectedTextColor != 0) selectedTextColor else defaultTextColor

        return ColorStateList(states, intArrayOf(disabledTextColor,pressedTextColor,selectedTextColor,defaultTextColor))
    }

    fun into(view: TextView){
        view.setTextColor(getTextColorStateList())
    }
}