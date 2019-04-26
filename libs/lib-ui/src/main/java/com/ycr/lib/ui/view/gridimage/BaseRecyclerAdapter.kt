package com.ycr.lib.ui.view.gridimage

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType

/**
 * Created by yuchengren on 2019/1/17.
 */
abstract class BaseRecyclerAdapter<T, VH : BaseRecyclerHolder>(var data: MutableList<T>?,  @LayoutRes private val itemResId: Int) : RecyclerView.Adapter<VH>() {
    private lateinit var context: Context
    private lateinit var layoutInflater: LayoutInflater
    var headerLayout: LinearLayout? = null

    var onItemClickListener: OnItemClickListener?= null
    var onItemLongClickListener: OnItemLongClickListener?= null

    var onItemChildClickListener: OnItemChildClickListener? = null
    var onItemChildLongClickListener: OnItemChildLongClickListener? = null
    var onItemChildHoverActionListener: OnHoverActionListener?= null

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    open fun getItem(position: Int): T? {
        if (position < 0 || position > itemCount - 1) {
            return null
        }
        return data?.get(position)
    }

    fun add(item: T) {
        if (data == null) {
            data = mutableListOf()
        }
        data?.add(item)
        notifyDataSetChanged()
    }

    fun addAll(newData: MutableList<T>?) {
        if (data == null) {
            data = mutableListOf()
        }
        newData?.run {
            data?.addAll(this)
            notifyDataSetChanged()
        }
    }

    fun replaceAll(newData: MutableList<T>?) {
        this.data = newData
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        if (position >= 0 && position < data?.size ?: 0) {
            data?.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun replace(newItem: T, position: Int) {
        if (position >= 0 && position < data?.size ?: 0) {
            data?.removeAt(position)
            data?.add(position, newItem)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, position, getItem(position))
    }

    abstract fun convert(holder: VH, position: Int, item: T?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        context = parent.context
        layoutInflater = LayoutInflater.from(context)
        val viewHolder = onCreateDefViewHolder(parent, viewType)
        bindViewClickListener(viewHolder)
        viewHolder.adapter = this
        return viewHolder
    }

    open fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createBaseViewHolder(parent, itemResId)
    }

    open fun createBaseViewHolder(parent: ViewGroup,  @LayoutRes layoutResId: Int): VH {
        return createBaseViewHolder(getItemView(layoutResId, parent))
    }

    open fun getItemView(@LayoutRes layoutResId: Int, parent: ViewGroup): View {
        return layoutInflater.inflate(layoutResId, parent, false)
    }

    fun getHeaderLayoutCount(): Int {
        return if (headerLayout == null || headerLayout?.childCount == 0) 0 else 1
    }

    private fun bindViewClickListener(baseViewHolder: BaseRecyclerHolder?) {
        val view = baseViewHolder?.itemView ?: return
        onItemClickListener?.run {
            view.setOnClickListener{
                onItemClick(this@BaseRecyclerAdapter, it, baseViewHolder.layoutPosition - getHeaderLayoutCount())
            }
        }

        onItemLongClickListener?.run {
            view.setOnClickListener{
                onItemLongClick(this@BaseRecyclerAdapter, it, baseViewHolder.layoutPosition - getHeaderLayoutCount())
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int): Boolean
    }

    interface OnItemChildClickListener {
        fun onItemChildClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int)
    }

    interface OnItemChildLongClickListener {
        fun onItemChildLongClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int): Boolean
    }

    interface OnHoverActionListener {
        fun onHoverEnter(adapter: BaseRecyclerAdapter<*, *>,view: View, position: Int,event: MotionEvent): Boolean
        fun onHoverMove(adapter: BaseRecyclerAdapter<*, *>,view: View, position: Int,event: MotionEvent): Boolean{ return true }
        fun onHoverExit(adapter: BaseRecyclerAdapter<*, *>,view: View, position: Int,event: MotionEvent): Boolean
    }

    /**
     * if you want to use subclass of BaseViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    open fun createBaseViewHolder(view: View): VH {
        var temp: Class<*>? = javaClass
        var z: Class<*>? = null
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp)
            temp = temp.superclass
        }
        val k: VH?
        // 泛型擦除会导致z为null
        if (z == null) {
            k = BaseRecyclerHolder(view) as VH
        } else {
            k = createGenericKInstance(z, view)
        }
        return if (k != null) k else BaseRecyclerHolder(view) as VH
    }

    /**
     * try to create Generic VH instance
     *
     * @param z
     * @param view
     * @return
     */
    private fun createGenericKInstance(z: Class<*>, view: View): VH? {
        try {
            val constructor: Constructor<*>
            // inner and unstatic class
            if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
                constructor = z.getDeclaredConstructor(javaClass, View::class.java)
                constructor.isAccessible = true
                return constructor.newInstance(this, view) as VH
            } else {
                constructor = z.getDeclaredConstructor(View::class.java)
                constructor.isAccessible = true
                return constructor.newInstance(view) as VH
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * get generic parameter VH
     *
     * @param z
     * @return
     */
    private fun getInstancedGenericKClass(z: Class<*>): Class<*>? {
        val type = z.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            for (temp in types) {
                if (temp is Class<*>) {
                    if (BaseRecyclerHolder::class.java.isAssignableFrom(temp)) {
                        return temp
                    }
                } else if (temp is ParameterizedType) {
                    val rawType = temp.rawType
                    if (rawType is Class<*> && BaseRecyclerHolder::class.java.isAssignableFrom(rawType)) {
                        return rawType
                    }
                }
            }
        }
        return null
    }
}