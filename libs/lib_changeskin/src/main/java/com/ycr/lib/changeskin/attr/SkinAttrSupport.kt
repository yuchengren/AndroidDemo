package com.ycr.lib.changeskin.attr

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.ycr.lib.changeskin.R
import com.ycr.lib.changeskin.constants.SkinConfig

/**
 * Created by yuchengren on 2018/8/29.
 */
object SkinAttrSupport{

    fun getSkinViews(activity: Activity): MutableList<SkinView>{
        val skinViews= mutableListOf<SkinView>()
        val view = activity.findViewById<ViewGroup>(android.R.id.content)
        addSkinViews(view,skinViews)
        return skinViews
    }

    fun addSkinViews(view: View, skinViews: MutableList<SkinView>) {
        val skinView = getSkinView(view)
        skinView?.let { skinViews.add(it) }
        if(view is ViewGroup){
            for (i in 0 until view.childCount){
                addSkinViews(view.getChildAt(i),skinViews)
            }
        }
    }

    private fun getSkinView(view: View): SkinView? {
        val tag = view.getTag(R.id.tag_skin)?: view.tag
        if(tag == null || tag !is String){
            return null
        }
        val skinAttrs = parseTag(tag)
        if(skinAttrs.isEmpty()){
            return null
        }
        changViewTag(view)
        return SkinView(view,skinAttrs)
    }

    private fun changViewTag(view: View) {
        if(view.getTag(R.id.tag_skin) == null){
            view.setTag(R.id.tag_skin,view.tag)
            view.tag = null
        }
    }

    private fun parseTag(tag: String): List<SkinAttr>{
        val skinAttrs = ArrayList<SkinAttr>()
        if(!tag.startsWith(SkinConfig.SKIN_PREFIX)){
            return skinAttrs
        }
        val tagSub = tag.replace(SkinConfig.SKIN_PREFIX,"")
        val tagSplitAttrs= tagSub.split(SkinConfig.SIGN_ATTR_SEPARATOR)
        tagSplitAttrs?.forEach {
            val attrTypeName = it.split(SkinConfig.SIGN_EQUAL)
            if(attrTypeName.size != 2){
                return@forEach
            }
            getSupportAttrType(attrTypeName[0])?.let {
                skinAttrs.add(SkinAttr(it,attrTypeName[1]))
            }
        }
        return skinAttrs
    }

    private fun getSupportAttrType(attrType: String): SkinAttrType?{
        SkinAttrType.values().forEach {
            if(it.attrType == attrType){
                return it
            }
        }
        return null
    }
}