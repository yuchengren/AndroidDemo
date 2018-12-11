package com.yuchengren.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.yuchengren.demo.R;

/**
 * Created by yuchengren on 2018/9/20.
 */
public class RoundImageView extends ImageView {
	private static final ScaleType SCALE_TYPE_DEFAULT = ScaleType.CENTER_CROP;
	private Paint mPaint;
	/**
	 * 圆角半径
	 */
	private int radius = dp2px(0);
	//宽高比，由我们自己设定
	private float ratio;
	public RoundImageView(Context context) {
		super(context);
		initViews();
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		obtainStyledAttrs(context, attrs, defStyleAttr);
		initViews();

	}

	private void obtainStyledAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
		radius = a.hasValue(R.styleable.RoundImageView_radius) ? a.getDimensionPixelSize(R.styleable.RoundImageView_radius, radius) : radius;
		ratio = a.getFloat(R.styleable.RoundImageView_ratio, 0.0f);
		a.recycle();
	}

	private void initViews() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		if(getScaleType() != SCALE_TYPE_DEFAULT){
			super.setScaleType(SCALE_TYPE_DEFAULT);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//获取宽度的模式和尺寸
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		//获取高度的模式和尺寸
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		//宽确定，高不确定
		if (widthMode == MeasureSpec.EXACTLY && (heightMode != MeasureSpec.EXACTLY || heightSize == 0 ) && ratio != 0) {
			heightSize = (int) (widthSize / ratio + 0.5f);//根据宽度和比例计算高度
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
		} else if ((widthMode != MeasureSpec.EXACTLY || widthSize == 0) && heightMode == MeasureSpec.EXACTLY && ratio != 0) {
			widthSize = (int) (heightSize * ratio + 0.5f);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
		}
		//必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable mDrawable = getDrawable();
		Matrix mDrawMatrix = getImageMatrix();
		if (mDrawable == null) {
			return; // couldn't resolve the URI
		}

		if (mDrawable.getIntrinsicWidth() == 0 || mDrawable.getIntrinsicHeight() == 0) {
			return;     // nothing to draw (empty bounds)
		}

		if (mDrawMatrix == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
			mDrawable.draw(canvas);
		} else {
			final int saveCount = canvas.getSaveCount();
			canvas.save();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				if (getCropToPadding()) {
					final int scrollX = getScrollX();
					final int scrollY = getScrollY();
					canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
							scrollX + getRight() - getLeft() - getPaddingRight(),
							scrollY + getBottom() - getTop() - getPaddingBottom());
				}
			}
			canvas.translate(getPaddingLeft(), getPaddingTop());
			if (radius != 0) {//当为圆角模式的时候
				Bitmap bitmap = drawable2Bitmap(mDrawable);
				mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
				canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom()),
						radius, radius, mPaint);
			} else {
				if (mDrawMatrix != null) {
					canvas.concat(mDrawMatrix);
				}
				mDrawable.draw(canvas);
			}
			canvas.restoreToCount(saveCount);
		}
	}

	/**
	 * drawable转换成bitmap
	 */
	private Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		//根据传递的scaletype获取matrix对象，设置给bitmap
		Matrix matrix = getImageMatrix();
		if (matrix != null) {
			canvas.concat(matrix);
		}
		drawable.draw(canvas);
		return bitmap;
	}

	private int dp2px(float value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}
}
