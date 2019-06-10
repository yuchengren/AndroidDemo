package com.ycr.kernel.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * 工具类
 *
 */

public class SystemBarUtils {
    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";
    public static int screenWidth;
    public static int screenHeight;
    public static int navigationHeight = 0;
    private static DisplayMetrics mMetrics;


    /**
     * 设置状态栏
     *
     * @param activity
     * @param on
     */
    public static void setTranslucentStatus(Activity activity, boolean on) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus_(activity, on);
        }
    }

    @TargetApi(19)
    private static void setTranslucentStatus_(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 关闭下拉菜单
     *
     * @return
     */
    public static void collapseStatusBar(Context context) {
        try {
            @SuppressLint("WrongConstant") Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod(
                        "collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarValue(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕物理高度~
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getScreenPoint(context).y;
    }

    public static Point getScreenPoint(Context context){
        Point size = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.getDefaultDisplay().getRealSize(size);
        }else{
            windowManager.getDefaultDisplay().getSize(size);
        }

//        DisplayMetrics dm = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            windowManager.getDefaultDisplay().getRealMetrics(dm);
//        }else{
//            windowManager.getDefaultDisplay().getMetrics(dm);
//        }
        return size;
    }

    /**
     * 获取底部导航栏高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        navigationHeight = resources.getDimensionPixelSize(resourceId);
        return navigationHeight;
    }

    public static boolean isNavigationBarShow(Context context){
        //如果Activity实际显示区域高度+状态栏区域高度 不等于 屏幕物理高度，则代表虚拟导航栏显示
        int screenHeight = getScreenHeight(context);
        if(context instanceof Activity){
            int activityWindowHeight = getActivityWindowHeight((Activity) context);
            if(activityWindowHeight != 0){
                if(screenHeight != getStatusBarHeight(context) + activityWindowHeight){
                    return true;
                }
            }
        }
        return false;
    }

    public static int getActivityWindowHeight(Activity activity){
        return getActivityWindowRect(activity).height();
    }

    public static Rect getActivityWindowRect(Activity activity){
        Rect outRect = new Rect();
        try {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return outRect;
    }
    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    /**
     * @param activity
     * @param useThemestatusBarColor   是否要状态栏的颜色，不设置则为透明色
     * @param withoutUseStatusBarColor 是否不需要使用状态栏为暗色调
     */
    public static void setStatusBar(Activity activity, boolean useThemestatusBarColor, boolean withoutUseStatusBarColor) {
        int originFlag = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = originFlag | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (useThemestatusBarColor) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(android.R.color.white));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !withoutUseStatusBarColor) {
            activity.getWindow().getDecorView().setSystemUiVisibility(originFlag | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static void reMeasure(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        mMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(mMetrics);
        } else {
            display.getMetrics(mMetrics);
        }

        screenWidth = mMetrics.widthPixels;
        screenHeight = mMetrics.heightPixels;
    }


    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
     */
    private static void processMIUI(boolean lightStatusBar, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), lightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * 判断手机是否是小米
     *
     * @return
     */
    public static boolean isMIUI() {
        String mf = Build.MANUFACTURER.toLowerCase();
        return mf.contains("xiaomi");
    }

    /**
     * 判断手机是否是魅族
     *
     * @return
     */
    public static boolean isFlyme() {
        String mf = Build.MANUFACTURER.toLowerCase();
        return mf.contains("meizu");
    }

    /**
     * 设置状态栏文字色值为深色调
     *
     * @param useDart  是否使用深色调
     * @param activity
     */
    public static void setStatusTextColor(boolean useDart, Activity activity) {
        int uiFlag = activity.getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (isFlyme()) {
            StatusBarColorUtils.setStatusBarDarkIcon(activity, useDart);
        } else {
            if (useDart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0
                    uiFlag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else if (isMIUI()) {
                    processMIUI(useDart, activity);
                } else {
                    // 不支持dart模式情况下，将状态栏颜色设置成半透明灰色
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
                        activity.getWindow().setStatusBarColor(Color.parseColor("#88aaaaaa"));
                    }
                    UtilModuleKt.getUtilLog().e("not support light status bar");
                }
            } else {
                uiFlag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(uiFlag);
    }


    public static boolean isStatusBarTranslucent(@NonNull Context context) {
        return ThemeUtil.getBoolean(context, android.R.attr.windowTranslucentStatus)
                || ThemeUtil.getBoolean(context, android.R.attr.windowDrawsSystemBarBackgrounds);
    }

}
