package com.ycr.kernel.union.constants;

import android.support.annotation.IntDef;

import static com.ycr.kernel.union.constants.ReferenceMode.PLANTOM;
import static com.ycr.kernel.union.constants.ReferenceMode.SOFT;
import static com.ycr.kernel.union.constants.ReferenceMode.STRONG;
import static com.ycr.kernel.union.constants.ReferenceMode.WEAK;

/**
 * 对象引用类型
 * Created by yuchengren on 2018/12/10.
 */
@IntDef({STRONG,SOFT,WEAK,PLANTOM})
public @interface ReferenceMode {
	/**
	 * 强引用
	 */
	int STRONG = 0;
	/**
	 * 软引用
	 */
	int SOFT = 1;
	/**
	 * 弱引用
	 */
	int WEAK = 2;
	/**
	 * 虚引用
	 */
	int PLANTOM = 3;
}
