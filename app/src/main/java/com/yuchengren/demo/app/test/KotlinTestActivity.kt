package com.yuchengren.demo.app.test

import android.os.Bundle
import android.os.Environment
import android.view.View
import com.ycr.kernel.image.CornerType
import com.ycr.kernel.image.glide.ImageDisplayOption
import com.ycr.kernel.log.LogHelper
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.union.helper.ImageHelper
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.entity.Student
import com.yuchengren.demo.R
import com.yuchengren.demo.app.body.login.LoginActivity
import kotlinx.android.synthetic.main.activity_kotlin_test.*
import java.util.*
import kotlin.collections.HashSet

/**
 * Created by yuchengren on 2019/1/28.
 */
class KotlinTestActivity: BaseActivity() {

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_kotlin_test
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)

        btn_test.setOnClickListener {
            LoginActivity.start(this)
            tv1.isEnabled = !tv1.isEnabled
        }


        btn_test2.setOnClickListener {
            tv2.isSelected = !tv2.isSelected
        }

       val imageOption = ImageDisplayOption.build().
                cornerType(CornerType.TOP).
                cornerRadius(ContextHelper.getDimenPixel(R.dimen.corner_radius_more))

        ImageHelper.display(ivTest,Environment.getExternalStorageDirectory().absolutePath + "/test.png",imageOption)


        val set = LinkedHashSet<Student>()
        set.add(Student("123","xiaoming"))
        set.add(Student("123","tiantian"))
        val array = intArrayOf(1,2,3)
        printArray(*array)

    }

    private fun printArray(vararg array: Int){
        array.forEach {
            LogHelper.d(it.toString())
        }

    }
}