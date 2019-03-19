package com.ycr.kernel.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class NotchScreenUtils {

    private static final String MF_HUAWEI = "huawei";
    private static final String MF_XIAOMI = "xiaomi";
    private static final String MF_OPPO = "oppo";
    private static final String MF_VIVO = "vivo";

    public static final int VIVO_NOTCH = 0x00000020;//是否有刘海

    public static boolean hasNotchInScreen(View view) {
        if(view == null) return false;
        boolean ret = false;
        if (Build.VERSION.SDK_INT < 28) {
            String mf = Build.MANUFACTURER.toLowerCase();
            if (mf.contains(MF_HUAWEI)) {
                try {
                    ClassLoader cl = view.getContext().getClassLoader();
                    Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
                    Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
                    ret = (boolean) get.invoke(HwNotchSizeUtil);
                } catch (ClassNotFoundException e) {
                    Log.e("test", "hasNotchInScreen ClassNotFoundException");
                } catch (NoSuchMethodException e) {
                    Log.e("test", "hasNotchInScreen NoSuchMethodException");
                } catch (Exception e) {
                    Log.e("test", "hasNotchInScreen Exception");
                } finally {
                    return ret;
                }
            } else if (mf.contains(MF_VIVO)) {
                try {
                    ClassLoader classLoader = view.getContext().getClassLoader();
                    Class FtFeature = classLoader.loadClass("android.util.FtFeature");
                    Method method = FtFeature.getMethod("isFeatureSupport", int.class);
                    ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
                } catch (ClassNotFoundException e) {
                    Log.e("Notch", "hasNotchAtVivo ClassNotFoundException");
                } catch (NoSuchMethodException e) {
                    Log.e("Notch", "hasNotchAtVivo NoSuchMethodException");
                } catch (Exception e) {
                    Log.e("Notch", "hasNotchAtVivo Exception");
                } finally {
                    return ret;
                }
            } else if (mf.contains(MF_OPPO)) {
                return view.getContext().getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
            } else if (mf.contains(MF_XIAOMI)) {
                String value = System.getProperty("ro.miui.notch");
                return "1".equals(value);
            }else{
                return ret;
            }
        } else {
            try {
                Object display = getDisplayCutout(view);
                if (display == null) {
                    return false;
                }
                Method displayMethod = display.getClass().getMethod("getBoundingRects");
                if(displayMethod == null) return false;
                List<Rect> list = (List<Rect>) displayMethod.invoke(display);
                if (list == null || list.size() == 0) {
                    ret = false;
                } else {
                    ret = true;
                }
                return ret;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private static Object getDisplayCutout(View view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Context context = view.getContext();
        if (context != null && context instanceof Activity) {
            View rootView = ((Activity) context).getWindow().getDecorView();
            if(rootView == null) return null;
            Method method = View.class.getMethod("getRootWindowInsets");
            if(method == null) return null;
            Object temp = method.invoke(rootView);
            if(temp == null) return null;
            Method cutoutMethod = temp.getClass().getMethod("getDisplayCutout");
            if(cutoutMethod == null) return null;
            Object display = cutoutMethod.invoke(temp);
            return display;
        }
        return null;
    }

    public static int[] getSafeInset(View view) {
        if (!hasNotchInScreen(view)) {
            return null;
        }
        int[] safeInset = new int[4];
        if (Build.VERSION.SDK_INT < 28) {
            String mf = Build.MANUFACTURER.toLowerCase();
            if (mf.contains(MF_HUAWEI)) {
                getHuaweiSafeInset(view.getContext(), safeInset);
            } else if (mf.contains(MF_VIVO)) {
                getVivoSafeInset(view.getContext(), safeInset);
            } else if (mf.contains(MF_OPPO)) {
                getOPPOSafeInset(safeInset);
            } else if (mf.contains(MF_XIAOMI)) {
                getXiaomiSafeInset(view.getContext(), safeInset);
            }
        }else{

            try {
                Object display = getDisplayCutout(view);
                Method leftMethod = display.getClass().getMethod("getSafeInsetLeft");
                Method rightMethod = display.getClass().getMethod("getSafeInsetRight");
                Method topMethod = display.getClass().getMethod("getSafeInsetTop");
                Method bottomMethod = display.getClass().getMethod("getSafeInsetBottom");
                if(leftMethod != null){
                    safeInset[0] = (int) leftMethod.invoke(display);
                }
                if(topMethod != null){
                    safeInset[1] = (int) topMethod.invoke(display);
                }
                if(rightMethod != null){
                    safeInset[2] = (int) rightMethod.invoke(display);
                }
                if(bottomMethod != null){
                    safeInset[3] = (int) bottomMethod.invoke(display);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return safeInset;
    }

    private static void getHuaweiSafeInset(Context context, int[] safeInset) {
        int[] ret;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
            if (ret == null || ret.length < 2) {
                return;
            }
            safeInset[0] = 0;
            safeInset[1] = ret[1];
            safeInset[2] = 0;
            safeInset[3] = 0;
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        }
    }

    private static void getVivoSafeInset(Context context, int[] safeInset) {
        safeInset[0] = 0;
        safeInset[1] = DensityUtils.dp2px(context, 27);
        safeInset[2] = 0;
        safeInset[3] = 0;
    }

    private static void getOPPOSafeInset(int[] safeInset) {
        safeInset[0] = 0;
        safeInset[1] = 80;
        safeInset[2] = 0;
        safeInset[3] = 0;
    }

    private static void getXiaomiSafeInset(Context context, int [] safeInset){
        safeInset[0] = 0;
        safeInset[1] = WindowBarUtils.getStatusBarHeight(context);
        safeInset[2] = 0;
        safeInset[3] = 0;
    }
}
