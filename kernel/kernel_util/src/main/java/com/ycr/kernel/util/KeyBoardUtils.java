package com.ycr.kernel.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * 软键盘相关工具类
 * 
 * @author zhy
 * 
 */
public class KeyBoardUtils {
	/**
	 * 打卡软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeyboard(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeyboard(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	public static void close(Context mContext, Activity mActivity){
		InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(inputMethodManager.isActive() && mActivity.getCurrentFocus() != null){
			inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
		}
	}

	/**
	 * 通过反射清空内存泄漏
	 * @param destContext
	 */
	public static void fixInputMethodManagerLeak(Context destContext) {
		if (destContext == null) {
			return;
		}
		InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager == null) {
			return;
		}

		String[] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
		Field filed;
		Object filedObject;
		for (String view : viewArray) {
			try {
				filed = inputMethodManager.getClass().getDeclaredField(view);
				if (filed == null) {
					continue;
				}
				if (!filed.isAccessible()) {
					filed.setAccessible(true);
				}
				filedObject = filed.get(inputMethodManager);
				if (filedObject != null && filedObject instanceof View) {
					View fileView = (View) filedObject;
					if (fileView.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
						filed.set(inputMethodManager, null); // 置空，破坏掉path to gc节点
					}
				}
			} catch (Throwable t) {
			}
		}
	}
}
