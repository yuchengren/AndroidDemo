package com.yuchengren.mvp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * 图片的高斯模糊工具类
 * Created by yuchengren on 2018/8/14.
 */
public class BlurUtil {

	/**
	 *
	 * @param context
	 * @param bitmap
	 * @param radius 通过设置模糊半径（radius）的大小来控制图片的清晰度
	 * @return
	 */
	public static Bitmap blur(Context context, Bitmap bitmap, float radius){
		/**
		 * RenderScript的效率要比fastBlur 好很多，但是还是有可能达不到16ms每一帧的要求而导致卡顿。所以需要进行优化。
		 * 通过缩小图片，使其丢失一些像素点，接着进行模糊化处理，然后再放大到原来尺寸。由于图片缩小后再进行模糊处理，
		 * 需要处理的像素点和半径都变小，从而使得模糊处理速度加快。
		 */
//		int width = Math.round(bitmap.getWidth() * scale);
//		int height = Math.round(bitmap.getHeight() * scale);
//		Bitmap inputBmp = Bitmap.createScaledBitmap(bitmap,width,height,false);

		/**
		 * RenderScript在SDK17以下 要使用兼容包 约160K
		 *在defaultConfig中配置
		 * // 使用support.v8.renderscript
		 * renderscriptTargetApi 18
		 * renderscriptSupportModeEnabled true
		 */
		if(Build.VERSION.SDK_INT >= 17){
			return rsBlur(context,bitmap,radius);
		}else{
			return fastBlur(bitmap,(int)radius,true);
		}
	}

	public static Bitmap rsBlur(Context context, Bitmap bitmap, float radius){
		//Let's create an empty bitmap with the same size of the bitmap we want to blur
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

		//Instantiate a new Renderscript
		RenderScript rs = RenderScript.create(context.getApplicationContext());

		//Create an Intrinsic Blur Script using the Renderscript
		ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

		//Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
		Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
		Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

		//Set the radius of the blur: 0 < radius <= 25
		blurScript.setRadius(radius);

		//Perform the Renderscript
		blurScript.setInput(allIn);
		blurScript.forEach(allOut);

		//Copy the final bitmap created by the out Allocation to the outBitmap
		allOut.copyTo(outBitmap);

		//recycle the original bitmap
		bitmap.recycle();

		//After finishing everything, we destroy the Renderscript.
		rs.destroy();

		return outBitmap;
	}

	// Stack Blur v1.0 from
	// http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
	//
	// Java Author: Mario Klingemann <mario at quasimondo.com>
	// http://incubator.quasimondo.com
	// created Feburary 29, 2004
	// Android port : Yahel Bouaziz <yahel at kayenko.com>
	// http://www.kayenko.com
	// ported april 5th, 2012

	// This is a compromise between Gaussian Blur and Box blur
	// It creates much better looking blurs than Box Blur, but is
	// 7x faster than my Gaussian Blur implementation.
	//
	// I called it Stack Blur because this describes best how this
	// filter works internally: it creates a kind of moving stack
	// of colors whilst scanning through the image. Thereby it
	// just has to add one new block of color to the right side
	// of the stack and remove the leftmost color. The remaining
	// colors on the topmost layer of the stack are either added on
	// or reduced by one, depending on if they are on the right or
	// on the left side of the stack.
	//
	// If you are using this algorithm in your code please add
	// the following line:
	//
	// Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
	// fastBlur java高斯模糊
	public static Bitmap fastBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {

		Bitmap bitmap;
		if (canReuseInBitmap) {
			bitmap = sentBitmap;
		} else {
			bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
		}

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);

		return (bitmap);
	}

}
