package com.yuchengren.demo.app.other

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ycr.lib.ui.view.gridimage.GridImageEntity
import com.ycr.lib.ui.view.gridimage.GridRecyclerView
import com.yuchengren.demo.R
import com.yuchengren.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_nine_grid.*

/**
 * Created by yuchengren on 2018/12/27.
 */
class NineGridActivity: BaseActivity() {


    override fun getRootLayoutResId(): Int {
        return R.layout.activity_nine_grid
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        val item = GridImageEntity("file://${Environment.getExternalStorageDirectory().absolutePath + "/test.png"}",true)
        val mutableList = mutableListOf<GridImageEntity>(item,item,item,item)

        gridRecyclerView.run {
            setNewData(mutableList)
            onEventListener = object : GridRecyclerView.OnEventListener{
                override fun onClickItem(url: String, position: Int) {
                }

                override fun onClickPlus() {
                }

                override fun loadUrl(url: String, view: ImageView) {
                    Glide.with(context).load(url).into(view)
                }
            }

        }

        rootView?.postDelayed({
//            Glide.with(this).load("file://${Environment.getExternalStorageDirectory().absolutePath + "/test.png"}").into(gridImageView)
//            gridImageView.setImageResource(R.drawable.test)
        },100)
    }

}