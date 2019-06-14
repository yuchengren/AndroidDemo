package com.ycr.lib.ui.util

import android.content.res.ColorStateList
import android.widget.TextView

/**
 * created by yuchengren on 2019-06-13
 */
class ColorStateBuilder {
    val state_disabled = intArrayOf(-android.R.attr.state_enabled)
    val state_pressed = intArrayOf(android.R.attr.state_pressed,android.R.attr.state_enabled)
    val state_selected = intArrayOf(android.R.attr.state_selected)

    val state_none = intArrayOf()

//    val states  = arrayOf(state_disabled,state_pressed,state_selected,state_none)

    var defaultTextColor: Int = 0
    var pressedTextColor: Int = 0
    var disabledTextColor: Int = 0
    var selectedTextColor: Int = 0


    private fun getTextColorStateList(): ColorStateList {
        val defaultTextColor = defaultTextColor
        val states = mutableListOf<IntArray>()
        val colors = mutableListOf<Int>()
        if(disabledTextColor != 0){
            states.add(state_disabled)
            colors.add(disabledTextColor)
        }
        if(pressedTextColor != 0){
            states.add(state_pressed)
            colors.add(pressedTextColor)
        }
        if(selectedTextColor != 0){
            states.add(state_selected)
            colors.add(selectedTextColor)
        }
        states.add(state_none)
        colors.add(defaultTextColor)

        return ColorStateList(states.toTypedArray(), colors.toTypedArray().toIntArray())
    }

    fun into(view: TextView){
        view.setTextColor(getTextColorStateList())
    }
}