package com.ycr.lib.permission

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ycr.module.base.R

/**
 * Created by yuchengren on 2019/3/15.
 */
class PermissionActionApdater(val actions: Array<PermissionAction>,var listener:((position:Int,item: PermissionAction) -> Unit)?): RecyclerView.Adapter<PermissionActionApdater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.permission_item_permission_action, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = actions[position]
        holder.tvAction.run {
            text = action.permissionDesc
            isEnabled = !action.isGranted
            setOnClickListener {
                listener?.invoke(position,action)
            }
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvAction = itemView.findViewById<TextView>(R.id.tvAction)
    }
}

