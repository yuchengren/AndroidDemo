package com.ycr.lib.ui.util;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by yuchengren on 2019/5/12
 */
public class ThemeUtil {

    public static int getColor(Context context, int colorAttr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{colorAttr});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public static int getDimension(Context context, int dimenAttr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{dimenAttr});
        int dimen = a.getDimensionPixelSize(0, 0);
        a.recycle();
        return dimen;
    }

    public static int getResourceId(Context context, int resAttr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{resAttr});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    public static boolean getBoolean(Context context, int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        boolean result = a.getBoolean(0, false);
        a.recycle();
        return result;
    }
}
