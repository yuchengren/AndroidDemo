package com.ycr.lib.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ycr.lib.theme.MessageDialogButtonStyle
import com.ycr.lib.theme.MessageDialogButtonType
import com.ycr.lib.ui.R
import java.io.Serializable

/**
 * created by yuchengren on 2019/5/11
 */
class MessageDialogFragment: DialogFragment() {

    companion object{
        const val ARG_BUILDER = "builder"

        @JvmStatic fun builder(): Builder{
            return Builder()
        }

        @JvmStatic fun dismiss(fragmentManager: FragmentManager,tag: String){
            (fragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismiss()
        }
    }

    private var builder: Builder?
        get() = arguments?.getSerializable(ARG_BUILDER) as? Builder
        set(value) {
            if(arguments == null){
                arguments = Bundle()
            }
            arguments?.putSerializable(ARG_BUILDER,value)
        }

    private var onButtonClickListener: OnButtonClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = builder?.cancelable?:true
        if(builder?.isSave != true && savedInstanceState != null){
            showsDialog = false
            dismissAllowingStateLoss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = checkNotNull(activity)
        val builder = checkNotNull(builder)

        builder.dialogView(createDialogView(activity,builder))
        builder.width(context?.resources?.getDimensionPixelSize(R.dimen.dialog_width)?:0)
        return builder.build(activity)
    }

    private fun createDialogView(context: Context,builder: Builder): View{
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_message,null,false) as ViewGroup
        val tvTitle = dialogView.findViewById<TextView>(R.id.message_dialog_title)
        val tvContent = dialogView.findViewById<TextView>(R.id.message_dialog_content)

        when{
            builder.title != null && !TextUtils.isEmpty(builder.title) -> tvTitle.text = builder.title
            builder.titleResId > 0  -> tvTitle.setText(builder.titleResId)
            else -> {
                tvTitle.visibility = View.GONE
                tvContent.setBackgroundResource(R.drawable.bg_dialog_message_title)
            }
        }

        var hasContextText = true
        when{
            builder.contentText != null -> tvContent.text = builder.contentText
            builder.contentTextResId > 0 -> tvContent.setText(builder.contentTextResId)
            else -> hasContextText = false
        }
        if(hasContextText){
            tvContent.run {
                gravity = builder.contextTextGravity
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                    tvContent.textAlignment = builder.contentTextAlignment
                }
            }
        }else{
//            tvContent.visibility = View.GONE
        }

        val buttonTextAndStyles = mutableListOf<Triple<String?,Int,MessageDialogButtonStyle?>?>().apply {
            if(builder.buttonTexts != null){
                builder.buttonTexts?.forEachIndexed { index, text ->
                    if(text.isEmpty()) return@forEachIndexed
                    add(Triple(text,getButtonTextResId(index,builder.buttonTextResIds),getButtonStyle(index,builder.buttonStyles)))
                }
            }else{
                builder.buttonTextResIds?.forEachIndexed { index, textResId ->
                    val text = if(textResId > 0) context.getString(textResId) else null
                    if(text == null || text.isEmpty()) return@forEachIndexed
                    add(Triple(text,textResId,getButtonStyle(index,builder.buttonStyles)))
                }
            }
            if(size == 2){
                add(1,null)
            }
        }
        val buttonSize = buttonTextAndStyles.size

        val isLeftRightButtonType = buttonSize == 3 && buttonTextAndStyles[1] == null

        for(i in 0..2){
            val buttonType = when(i){
                0 -> if(buttonSize > 1) MessageDialogButtonType.LEFT else MessageDialogButtonType.SINGLE
                1 -> MessageDialogButtonType.MIDDLE
                2 -> MessageDialogButtonType.RIGHT
                else -> throw IllegalStateException("get buttonType index more than 2")
            }

            val buttonText = if(buttonSize > i) buttonTextAndStyles[i]?.first else null
            val buttonTextResId = if(buttonSize > i) buttonTextAndStyles[i]?.second?:-1 else -1
            val buttonStyle = if(buttonSize > i) buttonTextAndStyles[i]?.third else null
            val index = if(isLeftRightButtonType && i == 2) i - 1 else i
            val button = getButton(context,dialogView,index,buttonType,buttonText,buttonTextResId,buttonStyle)
            dialogView.addView(button)
        }

        return dialogView
    }

    private fun getButton(context: Context,parent: ViewGroup,index: Int,type: String,text: String?,textResId: Int,style: MessageDialogButtonStyle?): View{
        val layoutResId = getButtonLayoutResId(context,type)
        val styleResId = getButtonStyleResId(context,style?: MessageDialogButtonStyle.WEAK)
        val button = LayoutInflater.from(context.apply { theme.applyStyle(styleResId,true) }).
                inflate(layoutResId,parent,false) as TextView
        return if(text == null || text.isEmpty()){
            button.apply { visibility = View.GONE }
        }else{
            button.apply {
                setText(text)
                setOnClickListener {
                    val onButtonClickListener = onButtonClickListener?:findParentOnButtonClickListener()
                    val isDismiss = onButtonClickListener?.onButtonClick(this@MessageDialogFragment,text,textResId,index)
                    //如果业务层返回true或者未设置listener,则底层进行Dismiss操作
                    if(isDismiss != false){
                        dismiss()
                    }
                }
            }
        }
    }

    private fun getButtonLayoutResId(context: Context, type: String): Int{
        return context.resources.getIdentifier("dialog_message_button_${type.toLowerCase()}","layout",context.packageName)
    }

    private fun getButtonStyleResId(context: Context,style: MessageDialogButtonStyle): Int{
        return context.resources.getIdentifier("MessageDialogButtonTheme${style.name.toLowerCase().capitalize()}","style",context.packageName)
    }

    private fun getButtonTextResId(buttonTextIndex: Int,buttonTextResIds: IntArray?): Int{
        if(buttonTextIndex < 0 || buttonTextResIds == null || buttonTextResIds.isEmpty() || buttonTextResIds.size <= buttonTextIndex){
            return -1
        }
        return buttonTextResIds[buttonTextIndex]
    }


    private fun getButtonStyle(buttonTextIndex: Int,buttonStyles: Array<out MessageDialogButtonStyle>?): MessageDialogButtonStyle{
        if(buttonTextIndex < 0 || buttonStyles == null || buttonStyles.isEmpty() || buttonStyles.size <= buttonTextIndex){
            return MessageDialogButtonStyle.WEAK
        }
        return buttonStyles[buttonTextIndex]
    }

    private fun findParentOnButtonClickListener(): OnButtonClickListener?{
        (parentFragment as? OnButtonClickListener)?.apply { return this }
        (activity as? OnButtonClickListener)?.apply { return this }
        return null
    }

    fun setOnButtonClickListener(onButtonClickListener: OnButtonClickListener): MessageDialogFragment{
        if(builder?.isSave == true){
            throw UnsupportedOperationException("set button click listener is not supported when in save mode")
        }
        this.onButtonClickListener = onButtonClickListener
        return this
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if(builder?.isSave == true){
            super.show(manager, tag)
        }else{
            showAllowStateLoss(manager,tag)
        }
    }

    /**
     * 若在show之前，activity进入onStop状态，会回调onSaveInstanceState方法，默认的show流程->commit->checkStateLoss(),
     * 导致if (mStateSaved) {throw new IllegalStateException("Can not perform this action after onSaveInstanceState");}
     * 如有延迟show的情况，在show之前，activity可能会进入后台，调用此方法show，避免异常
     */
    fun showAllowStateLoss(manager: FragmentManager?, tag: String?) {
        val ft = manager?.beginTransaction()
        ft?.add(this, tag)
        ft?.commitAllowingStateLoss()
    }

    interface OnButtonClickListener{
        /**
         * 返回true 代表需要底层处理dismissDialog
         */
        fun onButtonClick(dialog: MessageDialogFragment, text: String, textResId: Int, position: Int): Boolean
    }


    class Builder internal constructor(): BaseDialog.BaseBuilder<Builder>(),Serializable{
        internal var isSave: Boolean = false
        internal var title: CharSequence? = null
        internal var titleResId: Int = -1
        internal var contentText: CharSequence? = null
        internal var contentTextResId: Int = -1
        internal var contextTextGravity: Int = Gravity.CENTER
        internal var contentTextAlignment: Int = View.TEXT_ALIGNMENT_GRAVITY
        internal var buttonTextResIds: IntArray? = null
        internal var buttonTexts: Array<out String>? = null
        internal var buttonStyles: Array<out MessageDialogButtonStyle>? = null

        fun isSave(isSave: Boolean) = apply{
            this.isSave = isSave
        }

        fun title(title: CharSequence?) = apply{
            this.title = title
        }

        fun titleResId(titleResId: Int) = apply{
            this.titleResId = titleResId
        }

        fun contentText(contentText: CharSequence?) = apply{
            this.contentText = contentText
        }

        fun contentTextResId(contentTextResId: Int) = apply{
            this.contentTextResId = contentTextResId
        }

        fun contextTextGravity(contextTextGravity: Int) = apply{
            this.contextTextGravity = contextTextGravity
        }

        fun contentTextAlignment(contentTextAlignment: Int) = apply{
            this.contentTextAlignment = contentTextAlignment
        }

        fun buttonTextResIds(vararg buttonTextResIds: Int) = apply{
            this.buttonTextResIds = buttonTextResIds
        }

        fun buttonTexts(vararg buttonTexts: String) = apply{
            this.buttonTexts = buttonTexts
        }

        fun buttonStyles(vararg buttonStyles: MessageDialogButtonStyle) = apply{
            this.buttonStyles = buttonStyles
        }

        fun build(): MessageDialogFragment{
            val buttonSize = checkButtonSize()
            if(buttonStyles == null){
                setDefaultButtonStyle(buttonSize)
            }else{
                checkButtonStyle(buttonSize)
            }
            return MessageDialogFragment().apply { builder = this@Builder }
        }

        private fun setDefaultButtonStyle(buttonSize: Int) {
            this.buttonStyles = when(buttonSize){
                1 -> arrayOf(MessageDialogButtonStyle.STRONG)
                2 -> arrayOf(MessageDialogButtonStyle.WEAK,MessageDialogButtonStyle.STRONG)
                3 -> arrayOf(MessageDialogButtonStyle.WEAK,MessageDialogButtonStyle.WEAK,MessageDialogButtonStyle.STRONG)
                else -> arrayOf(MessageDialogButtonStyle.STRONG)
            }
        }

        private fun checkButtonSize(): Int{
            var buttonTextSize  = 0
            val buttonTextValid =  if(buttonTexts != null){
                buttonTexts?.run {
                    buttonTextSize = size
                    size in 1..3
                }?:false
            }else{
                buttonTextResIds?.run {
                    buttonTextSize = size
                    size in 1..3
                }?:false
            }
            check(buttonTextValid){
                "button text size is not in 1..3"
            }
            return buttonTextSize
        }

        private fun checkButtonStyle(buttonTextSize: Int){
            buttonStyles?.run {
                check(size == 1 || buttonTextSize == size){
                    "button style size is not 1 or button text size"
                }
            }
        }

        fun show(fragmentManager: FragmentManager,tag: String){
            build().show(fragmentManager,tag)
        }
    }
}