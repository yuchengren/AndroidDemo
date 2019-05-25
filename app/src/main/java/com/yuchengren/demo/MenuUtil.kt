package com.yuchengren.demo

import android.content.Context
import android.content.Intent

import com.yuchengren.demo.app.adaptation.AndroidEightAdaptationActivity
import com.yuchengren.demo.app.adaptation.AndroidNineAdaptationActivity
import com.yuchengren.demo.app.adaptation.AndroidSevenAdaptationActivity
import com.yuchengren.demo.app.other.ChangeThemeActivity
import com.yuchengren.demo.app.other.CallPhoneBackActivity
import com.yuchengren.demo.app.other.ImageEditActivity
import com.yuchengren.demo.app.other.GridActivity
import com.yuchengren.demo.app.other.RxAndroidActivity
import com.ycr.module.photo.chosen.ChosenPhotoActivity
import com.ycr.module.base.constant.MapKey
import com.ycr.module.base.constant.MenuCode
import com.ycr.module.base.entity.db.MenuEntity
import com.yuchengren.demo.app.body.main.MainActivity
import com.yuchengren.demo.app.test.KotlinTestActivity
import com.yuchengren.demo.app.test.TestActivity
import com.yuchengren.demo.app.widgettest.refreshview.RefreshViewActivity

/**
 * Created by yuchengren on 2017/12/20.
 */

object MenuUtil {

    @JvmStatic fun startActivity(context: Context, menuEntity: MenuEntity?) {
        if (menuEntity == null || menuEntity.code == null) {
            return
        }
        val clazz = when (menuEntity.code) {
            MenuCode.Second.CALL_PHONE_BACK -> CallPhoneBackActivity::class.java
            MenuCode.Second.RX_ANDROID -> RxAndroidActivity::class.java
            MenuCode.Second.TEST -> TestActivity::class.java
            MenuCode.Second.KOTLIN_TEST -> KotlinTestActivity::class.java
            MenuCode.Second.THEME_SWITCH -> ChangeThemeActivity::class.java
            MenuCode.Second.IMAGE_EDIT -> ImageEditActivity::class.java
            MenuCode.Second.NINE_GRID -> GridActivity::class.java
            MenuCode.Second.CHOOSE_PHOTO -> ChosenPhotoActivity::class.java
            MenuCode.Second.SEVEN_ADAPTATION -> AndroidSevenAdaptationActivity::class.java
            MenuCode.Second.EIGHT_ADAPTATION -> AndroidEightAdaptationActivity::class.java
            MenuCode.Second.NINE_ADAPTATION -> AndroidNineAdaptationActivity::class.java
            MenuCode.Second.REFRESH_VIEW -> RefreshViewActivity::class.java
            else -> MainActivity::class.java
        }
        val intent: Intent = Intent(context,clazz).apply {
            putExtra(MapKey.CODE, menuEntity.code)
            putExtra(MapKey.NAME, menuEntity.name)
        }
        context.startActivity(intent)
    }
}
