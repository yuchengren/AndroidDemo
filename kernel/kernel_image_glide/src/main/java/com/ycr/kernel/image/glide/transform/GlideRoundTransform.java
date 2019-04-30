package com.ycr.kernel.image.glide.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.ycr.kernel.image.CornerType;
import com.ycr.kernel.image.ImageDisplayType;

import java.security.MessageDigest;

/**
 * created by yuchengren on 2019/4/30
 */
public class GlideRoundTransform extends BitmapTransformation{

    private int cornerRadius;
    private int cornerType;
    private ImageDisplayType displayType;

    public GlideRoundTransform(int cornerRadius, int cornerType, ImageDisplayType displayType){
        this.cornerRadius = cornerRadius;
        this.cornerType = cornerType;
        this.displayType = displayType;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = toTransform;
        if(displayType == ImageDisplayType.CENTER_CROP){
            bitmap = TransformationUtils.centerCrop(pool,toTransform,outWidth,outHeight);
        }else if(displayType == ImageDisplayType.CENTER_INSIDE){
            bitmap = TransformationUtils.centerInside(pool,toTransform,outWidth,outHeight);
        }else if(displayType == ImageDisplayType.FIT_CENTER){
            bitmap = TransformationUtils.fitCenter(pool,toTransform,outWidth,outHeight);
        }else if(displayType == ImageDisplayType.CIRCLE_CROP){
            bitmap = TransformationUtils.circleCrop(pool,toTransform,outWidth,outHeight);
        }
        return roundCrop(pool,bitmap);
    }

    private Bitmap roundCrop(BitmapPool pool ,  Bitmap source){
        if(cornerRadius == 0){
            return source;
        }
        Bitmap result = pool.get(source.getWidth(),source.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//通过SRC_IN的模式取源图片和圆角矩形重叠的部分

        RectF rectF = new RectF(0f,0f,source.getWidth(),source.getHeight());
        canvas.drawRoundRect(rectF,cornerRadius,cornerRadius,paint);
        if((cornerType & CornerType.LEFT_TOP) == 0){
            canvas.drawRect(0f,0f,cornerRadius,cornerRadius,paint);
        }
        if((cornerType & CornerType.RIGHT_TOP) == 0){
            canvas.drawRect(rectF.right - cornerRadius,0f,rectF.right,cornerRadius,paint);
        }
        if((cornerType & CornerType.RIGHT_BOTTOM) == 0){
            canvas.drawRect(rectF.right - cornerRadius,rectF.bottom - cornerRadius,rectF.right,rectF.bottom,paint);
        }
        if((cornerType & CornerType.LEFT_BOTTOM) == 0){
            canvas.drawRect(0f,rectF.bottom - cornerRadius,cornerRadius,rectF.bottom,paint);
        }
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//        messageDigest.update(ByteBuffer.allocate(Integer.SIZE).array());
    }
}
