package com.ycr.module.base.permission

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ycr.lib.permission.PermissionAction
import com.ycr.module.base.R

/**
 * Created by yuchengren on 2019/3/15.
 */
class PermissionActionAdapter(val actions: Array<out PermissionAction>, var listener:((position:Int, item: PermissionAction) -> Unit)?): RecyclerView.Adapter<PermissionActionAdapter.ViewHolder>() {

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
            text = if(TextUtils.isEmpty(action.permissionDesc) && action.permissions.isNotEmpty())
                PermissionActionDescHelper.getActionDesc(action.permissions[0]) else action.permissionDesc
            isEnabled = !action.isGranted
            setOnClickListener {
                listener?.invoke(position,action)
            }
        }
        holder.ivGranted.run {
            visibility = if(action.isGranted) View.VISIBLE else View.GONE
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvAction = itemView.findViewById<TextView>(R.id.tvAction)
        val ivGranted = itemView.findViewById<ImageView>(R.id.ivGranted)
    }
}

