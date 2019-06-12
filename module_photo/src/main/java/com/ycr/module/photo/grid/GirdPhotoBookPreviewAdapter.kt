package com.ycr.module.photo.grid
import com.ycr.lib.ui.recyclerview.BaseRecyclerHolder
import com.ycr.lib.ui.recyclerview.grid.GridImageRecyclerAdapter
import com.ycr.module.photo.R

/**
 * Created by yuchengren on 2019/2/1.
 */
class GirdPhotoBookPreviewAdapter(data: MutableList<String>?): GridImageRecyclerAdapter(data,R.layout.item_grid_image_photo_book) {

    override fun convert(holder: BaseRecyclerHolder, position: Int, item: String?) {
        super.convert(holder, position, item)
        holder.addOnHoverListener(R.id.ivPreview)
        holder.addOnClickListener(R.id.ivPreview)
        holder.addOnClickListener(R.id.imageView)
    }
}