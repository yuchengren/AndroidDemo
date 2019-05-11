package com.ycr.module.photo.grid

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.ycr.lib.ui.gridimage.BaseRecyclerAdapter
import com.ycr.module.base.BaseActivity
import com.ycr.module.photo.R
import com.ycr.module.photo.clip.PhotoClipActivity
import com.ycr.module.photo.preview.PreviewPhotoActivity
import kotlinx.android.synthetic.main.activity_photo_book_grid.*

/**
 * Created by yuchengren on 2019/1/25.
 */
class PhotoBookGirdActivity: BaseActivity() {

    companion object {
        // 不同loader定义
        const val LOADER_ALL = 0
        const val LOADER_CATEGORY = 1

        fun start(context: Context){
            val intent = Intent(context,PhotoBookGirdActivity::class.java)
            context.startActivity(intent)
        }
    }

    var photoPreviewPop: PopupWindow? = null

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_photo_book_grid
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)

        tvCancel.setOnClickListener { onBackPressed() }

        val adapter = GirdPhotoBookPreviewAdapter(null).apply {
            setOnItemLoadImageListener { item, view, position ->
                Glide.with(this@PhotoBookGirdActivity).load(item).into(view)
            }

            onItemChildClickListener = object : BaseRecyclerAdapter.OnItemChildClickListener{
                override fun onItemChildClick(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int) {
                    val picUrl = getItem(position) ?: return
                    when(view.id){
                        R.id.ivPreview -> PreviewPhotoActivity.start(this@PhotoBookGirdActivity,picUrl)
                        else -> PhotoClipActivity.start(this@PhotoBookGirdActivity,picUrl)
                    }
                }
            }

            onItemChildHoverActionListener = object : BaseRecyclerAdapter.OnHoverActionListener{
                override fun onHoverEnter(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int, event: MotionEvent): Boolean {
                    showPhotoPreviewPop((view.parent as? View)?:return false,getItem(position)?:return false)
                    return true
                }

                override fun onHoverExit(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int, event: MotionEvent): Boolean {
                    dismissPhotoPreviewPop()
                    return true
                }

                override fun onHoverMove(adapter: BaseRecyclerAdapter<*, *>, view: View, position: Int, event: MotionEvent): Boolean {
                    return super.onHoverMove(adapter, view, position, event)
                }

            }
        }

        gridRecyclerView.adapter = adapter

        supportLoaderManager.initLoader(LOADER_ALL,null,object : LoaderManager.LoaderCallbacks<Cursor>{
            private val IMAGE_PROJECTION = arrayOf(MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media._ID)
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
                return when(id){
                    LOADER_ALL ->{
                        CursorLoader(this@PhotoBookGirdActivity,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                IMAGE_PROJECTION,null,null,"${IMAGE_PROJECTION[2]} DESC")
                    }
                    LOADER_CATEGORY -> {
                        CursorLoader(this@PhotoBookGirdActivity,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                IMAGE_PROJECTION,"${IMAGE_PROJECTION[0]} like '%${args?.getString("path")}%'",
                                null,"${IMAGE_PROJECTION[2]} DESC")
                    }
                    else -> null
                }
            }

            override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
                if(data == null || data.count == 0){
                    return
                }
                val imageList = mutableListOf<String>()
                data.moveToFirst()
                do {
                    val path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
                    val name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]))
                    val time = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))
                    imageList.add(path)
                }while (data.moveToNext())

                adapter.replaceAll(imageList)
            }

            override fun onLoaderReset(loader: Loader<Cursor>?) {}
        })

    }

    private fun showPhotoPreviewPop(itemView: View, picPath: String) {
        if(photoPreviewPop == null){
            photoPreviewPop = PopupWindow(this).apply {
                isFocusable = false
                isOutsideTouchable = true
                setBackgroundDrawable(BitmapDrawable(resources))
                contentView = View.inflate(this@PhotoBookGirdActivity, R.layout.pop_photo_preview,null)
            }
        }
        val itemViewWidth = itemView.width
        val itemViewHeight = itemView.height
        val locationViewCoordinateArray = IntArray(2)
        itemView.getLocationOnScreen(locationViewCoordinateArray)
//        val itemViewRightBottomX = locationViewCoordinateArray[0] + itemViewWidth
//        val itemViewRightBottomY = locationViewCoordinateArray[1] + itemViewHeight

        val bitmap = BitmapFactory.decodeFile(picPath)
        val photoRatio = bitmap.width / bitmap.height.toFloat()
        val popWidth: Int
        val popHeight: Int
        if(photoRatio > 1){
            popWidth = 3 * itemViewWidth
            popHeight = (popWidth / photoRatio).toInt()
        }else{
            popHeight = 3 * itemViewWidth
            popWidth = (popHeight * photoRatio).toInt()
        }
        val showLocationX = if(locationViewCoordinateArray[0] + itemViewWidth / 2f < windowManager.defaultDisplay.width / 2f){
            locationViewCoordinateArray[0]+ itemViewWidth
        }else{
            locationViewCoordinateArray[0] - popWidth
        }
        val showLocationY = if(locationViewCoordinateArray[1] - popHeight < 0) 0 else locationViewCoordinateArray[1] - popHeight

        photoPreviewPop?.apply {
            width = popWidth
            height = popHeight
            contentView.apply {
                findViewById<ImageView>(R.id.ivPhoto).setImageBitmap(bitmap)
            }
        }?.showAtLocation(gridRecyclerView, Gravity.NO_GRAVITY,showLocationX,showLocationY)
    }

    private fun dismissPhotoPreviewPop() {
        photoPreviewPop?.run {
            if(isShowing){
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissPhotoPreviewPop()
        photoPreviewPop = null
    }


}