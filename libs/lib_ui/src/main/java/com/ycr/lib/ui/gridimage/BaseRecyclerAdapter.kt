package com.ycr.lib.ui.gridimage

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType

/**
 * Created by yuchengren on 2019/1/17.
 */
abstract class BaseRecyclerAdapter<T, VH : BaseRecyclerHolder>(
        protected var data: MutableList<T>?,
        @LayoutRes protected val itemResId: Int
) : RecyclerView.Adapter<VH>() {

    companion object {
        const val HEADER_VIEW = 0x111
        const val FOOTER_VIEW = 0x333
        const val EMPTY_VIEW = 0x555
    }

    protected var context: Context? = null
    protected var layoutInflater: LayoutInflater? = null

    private var headerLayout: LinearLayout? = null
    private var footerLayout: LinearLayout? = null
    private var emptyLayout: FrameLayout? = null
    var showHeaderWhenEmpty: Boolean = false
    var showFooterWhenEmpty: Boolean = false

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    var onItemChildClickListener: OnItemChildClickListener? = null
    var onItemChildLongClickListener: OnItemChildLongClickListener? = null
    var onItemChildHoverActionListener: OnHoverActionListener? = null

    fun setOnItemClickListener(action: (BaseRecyclerAdapter<*, *>, View, Int) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int) {
                action(adapter, view, position)
            }
        }
    }

    fun setOnItemChildClickListener(action: (BaseRecyclerAdapter<*, *>, View, Int) -> Unit) {
        onItemChildClickListener = object : OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int) {
                action(adapter, view, position)
            }
        }
    }

    fun addHeaderView(header: View): Int {
        return addHeaderView(header, -1)
    }

    fun addHeaderView(header: View, index: Int): Int {
        return addHeaderView(header, index, LinearLayout.VERTICAL)
    }

    fun addHeaderView(header: View, index: Int, orientation: Int): Int {
        var addChildIndex = index
        if (headerLayout == null) {
            headerLayout = LinearLayout(header.context)
            if (orientation == LinearLayout.VERTICAL) {
                headerLayout?.orientation = LinearLayout.VERTICAL
                headerLayout?.layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT)
            } else {
                headerLayout?.orientation = LinearLayout.HORIZONTAL
                headerLayout?.layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.WRAP_CONTENT,
                        RecyclerView.LayoutParams.MATCH_PARENT)
            }
        }
        val childCount = headerLayout?.childCount ?: 0
        if (addChildIndex < 0 || addChildIndex > childCount) {
            addChildIndex = childCount
        }
        headerLayout?.addView(header, addChildIndex)
        if (headerLayout?.childCount == 1) {
            val position = getHeaderViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return addChildIndex
    }

    open fun getDataSourceSize(): Int {
        return data?.size ?: 0
    }

    fun removeHeaderView(header: View) {
        if (getHeaderLayoutCount() == 0) return

        headerLayout?.removeView(header)
        if (headerLayout?.childCount == 0) {
            val position = getHeaderViewPosition()
            if (position != -1) {
                notifyItemRemoved(position)
            }
        }
    }

    fun removeAllHeaderView() {
        if (getHeaderLayoutCount() == 0) return

        headerLayout?.removeAllViews()
        val position = getHeaderViewPosition()
        if (position != -1) {
            notifyItemRemoved(position)
        }
    }

    fun getHeaderLayoutCount() = if (headerLayout == null || headerLayout?.childCount == 0) 0 else 1

    fun getHeaderViewPosition(): Int {
        if (getEmptyViewCount() == 1) {
            if (showHeaderWhenEmpty) {
                return 0
            }
        } else {
            return 0
        }
        return -1
    }

    fun addFooterView(footer: View): Int {
        return addFooterView(footer, -1)
    }

    fun addFooterView(footer: View, index: Int): Int {
        return addFooterView(footer, index, LinearLayout.VERTICAL)
    }

    fun addFooterView(footer: View, index: Int, orientation: Int): Int {
        var addChildIndex: Int = index
        if (footerLayout == null) {
            footerLayout = LinearLayout(footer.context)
            if (orientation == LinearLayout.VERTICAL) {
                footerLayout?.orientation = LinearLayout.VERTICAL
                footerLayout?.layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT)
            } else {
                footerLayout?.orientation = LinearLayout.HORIZONTAL
                footerLayout?.layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.WRAP_CONTENT,
                        RecyclerView.LayoutParams.MATCH_PARENT)
            }
        }
        val childCount = footerLayout?.childCount ?: 0
        if (addChildIndex < 0 || addChildIndex > childCount) {
            addChildIndex = childCount
        }
        footerLayout?.addView(footer, addChildIndex)
        if (footerLayout?.childCount == 1) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return addChildIndex
    }

    fun removeFooterView(footer: View) {
        if (getFooterLayoutCount() == 0) return

        footerLayout?.removeView(footer)
        if (footerLayout?.childCount == 0) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemRemoved(position)
            }
        }
    }

    fun removeAllFooterView() {
        if (getFooterLayoutCount() == 0) return

        footerLayout?.removeAllViews()
        val position = getFooterViewPosition()
        if (position != -1) {
            notifyItemRemoved(position)
        }
    }

    fun getFooterLayoutCount() = if (footerLayout == null || footerLayout?.childCount == 0) 0 else 1

    fun getFooterViewPosition(): Int {
        if (getEmptyViewCount() == 1) {
            var position = 1
            if (showHeaderWhenEmpty) {
                position++
            }
            if (showFooterWhenEmpty) {
                return position
            }
        } else {
            return getHeaderLayoutCount() + getDataSourceSize()
        }
        return -1
    }

    fun setEmptyView(emptyView: View) {
        var insert = false
        if (emptyLayout == null) {
            emptyLayout = FrameLayout(emptyView.context)
            val layoutParams: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT)
            if (emptyView.layoutParams != null) {
                emptyView.layoutParams.width = layoutParams.width
                emptyView.layoutParams.height = layoutParams.height
            }
            emptyLayout?.layoutParams = layoutParams
            insert = true
        }
        emptyLayout?.removeAllViews()
        emptyLayout?.addView(emptyView)
        if (insert) {
            if (getEmptyViewCount() == 1) {
                var position = 0
                if (showHeaderWhenEmpty && getHeaderLayoutCount() != 0) {
                    position++
                }
                notifyItemInserted(position)
            }
        }
    }

    fun getEmptyViewCount() = if (emptyLayout == null
            || emptyLayout?.childCount == 0
            || getDataSourceSize() != 0) 0 else 1

    override fun getItemCount(): Int {
        var count: Int
        if (getEmptyViewCount() == 1) {
            count = 1
            if (showHeaderWhenEmpty
                    && getHeaderLayoutCount() != 0) {
                count++
            }
            if (showFooterWhenEmpty
                    && getFooterLayoutCount() != 0) {
                count++
            }
        } else {
            count = getHeaderLayoutCount() + getDataSourceSize() + getFooterLayoutCount()
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        if (getEmptyViewCount() == 1) {
            val header = showHeaderWhenEmpty && getHeaderLayoutCount() != 0
            when (position) {
                0 -> {
                    if (header) {
                        return HEADER_VIEW
                    } else {
                        return EMPTY_VIEW
                    }
                }
                1 -> {
                    if (header) {
                        return EMPTY_VIEW
                    } else {
                        return FOOTER_VIEW
                    }
                }
                2 -> {
                    return FOOTER_VIEW
                }
                else -> return EMPTY_VIEW
            }
        } else {
            val numHeader = getHeaderLayoutCount()
            if (position < numHeader) {
                return HEADER_VIEW
            } else {
                var adjustPosition = position - numHeader
                if (adjustPosition < getDataSourceSize()) {
                    return getDefItemViewType(adjustPosition)
                } else {
                    return FOOTER_VIEW
                }
            }
        }
    }

    open protected fun getDefItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun getDataList(): MutableList<T>? {
        return data
    }

    open fun getItem(position: Int): T? {
        if (position < 0 || position > getDataSourceSize() - 1) {
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

    fun addAll(newData: List<T>?) {
        if (data == null) {
            data = mutableListOf()
        }
        newData?.run {
            data?.addAll(this)
            notifyDataSetChanged()
        }
    }

    fun replaceAll(newData: List<T>?) {
        data?.clear()
        addAll(newData)
    }

    fun setNewData(newData: MutableList<T>?) {
        data = newData
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        if (position >= 0 && position < getDataSourceSize()) {
            data?.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun replace(newItem: T, position: Int) {
        if (position >= 0 && position < getDataSourceSize()) {
            data?.removeAt(position)
            data?.add(position, newItem)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        data?.let {
            it.clear()
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when (getItemViewType(position)) {
            HEADER_VIEW -> {
            }
            FOOTER_VIEW -> {
            }
            EMPTY_VIEW -> {
            }
            else -> {
                convert(holder, position, getItem(position - getHeaderLayoutCount()))
            }
        }
    }

    abstract fun convert(holder: VH, position: Int, item: T?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val baseViewHolder: BaseRecyclerHolder
        context = parent.context
        layoutInflater = LayoutInflater.from(context)
        when (viewType) {
            EMPTY_VIEW -> baseViewHolder = createBaseViewHolder(emptyLayout!!)
            HEADER_VIEW -> baseViewHolder = createBaseViewHolder(headerLayout!!)
            FOOTER_VIEW -> baseViewHolder = createBaseViewHolder(footerLayout!!)
            else -> {
                baseViewHolder = onCreateDefViewHolder(parent, viewType)
                bindViewClickListener(baseViewHolder)
            }
        }
        baseViewHolder.adapter = this
        return baseViewHolder
    }

    open fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createBaseViewHolder(parent, viewType)
    }

    open fun createBaseViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createBaseViewHolder(getItemView(parent, viewType))
    }

    open fun getItemView(parent: ViewGroup, viewType: Int): View {
        return layoutInflater!!.inflate(getItemViewResId(viewType), parent, false)
    }

    open fun getItemViewResId(viewType: Int): Int {
        return itemResId
    }

    private fun bindViewClickListener(baseViewHolder: BaseRecyclerHolder?) {
        val view = baseViewHolder?.itemView ?: return
        onItemClickListener?.run {
            view.setOnClickListener {
                onItemClick(this@BaseRecyclerAdapter, it, baseViewHolder.layoutPosition - getHeaderLayoutCount())
            }
        }

        onItemLongClickListener?.run {
            view.setOnClickListener {
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
        fun onHoverEnter(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int, event: MotionEvent): Boolean
        fun onHoverMove(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int, event: MotionEvent): Boolean {
            return true
        }

        fun onHoverExit(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int, event: MotionEvent): Boolean
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

    /**
     * fix item span size
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            if (layoutManager is GridLayoutManager) {
                (layoutManager as GridLayoutManager).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = getItemViewType(position)
                            if (isFixViewType(viewType))
                                return spanCount
                            return 1
                        }
                    }
                }
            }
        }
    }

    private fun isFixViewType(viewType: Int): Boolean {
        return viewType == HEADER_VIEW || viewType == FOOTER_VIEW || viewType == EMPTY_VIEW
    }
}