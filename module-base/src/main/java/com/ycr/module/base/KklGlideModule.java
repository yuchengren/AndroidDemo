package com.ycr.module.base;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 *
 * 应用层的Glide Configuration. 可以用来配置 ApplicationOptions, 也可以用来注册Component
 */
@GlideModule
public class KklGlideModule extends AppGlideModule{

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory())
    }
}
