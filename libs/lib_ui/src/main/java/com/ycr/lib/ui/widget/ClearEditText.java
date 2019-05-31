package com.ycr.lib.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.ycr.lib.ui.R;


/**
 * @说明： 自定义带删除按钮的EditText
 */
public class ClearEditText extends AppCompatEditText implements OnFocusChangeListener,
        TextWatcher {
    // EditText右侧的删除按钮
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;
    private OnTextChangedListener onTextChangedListener;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        mClearDrawable = getCompoundDrawables()[2];

        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.edittext_delete_selector);
        }

        int width = mClearDrawable.getIntrinsicWidth();
        int height = mClearDrawable.getIntrinsicHeight();

        mClearDrawable.setBounds(0, 0, width, height);
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

	/*
	   @说明：isInnerWidth, isInnerHeight为true，触摸点在删除图标之内，则视为点击了删除图标 event.getX()
	         获取相对应自身左上角的X坐标 event.getY() 获取相对应自身左上角的Y坐标 getWidth() 获取控件的宽度 getHeight()
	         获取控件的高度 getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离 getPaddingRight()
	         获取删除图标右边缘到控件右边缘的距离 isInnerWidth: getWidth() - getTotalPaddingRight()
	         计算删除图标左边缘到控件左边缘的距离 getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
	   isInnerHeight: distance 删除图标顶部边缘到控件顶部边缘的距离 distance + height
	          删除图标底部边缘到控件顶部边缘的距离
	 */

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int) event.getX();
                if (x > (getWidth() - getTotalPaddingRight())) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候， 输入长度为零，隐藏删除图标，否则，显示删除图标
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
        if(onTextChangedListener != null){
            onTextChangedListener.onTextChanged(s,start,count,after,s.length() == 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener){
        this.onTextChangedListener = onTextChangedListener;
    }

    public interface OnTextChangedListener{
        void onTextChanged(CharSequence s, int start, int count, int after, boolean isEmpty);
    }
}
