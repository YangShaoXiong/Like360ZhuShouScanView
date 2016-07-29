package com.dreamer.library;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by ysx on 2016/7/29.
 */
public class ScanView extends View{

    private Bitmap mBitmap1, mBitmap2, mBitmap3, mBitmap4;
    private Matrix matrix1, matrix2;
    private Paint mPaint1, mPaint2, mPaint3, mPaint4;

    private int mDegrees;
    private float mFactor = 0.7f;

    private ValueAnimator mValueAnimator, mValueAnimator2;

    public ScanView(Context context) {
        this(context, null);
    }

    public ScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context ctx) {
        mBitmap1 = BitmapFactory.decodeResource(ctx.getResources(),
                    R.drawable.frequent_soft_ripple_bg_1);
        mBitmap2 = BitmapFactory.decodeResource(ctx.getResources(),
                    R.drawable.frequent_soft_ripple_bg_2);
        mBitmap3 = BitmapFactory.decodeResource(ctx.getResources(),
                    R.drawable.frequent_soft_ripple_bg_3);
        mBitmap4 = BitmapFactory.decodeResource(ctx.getResources(),
                    R.drawable.frequent_soft_ripple_bg_4);

        matrix1 = new Matrix();
        matrix2 = new Matrix();

        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);

        mValueAnimator = ValueAnimator.ofInt(0, 360);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegrees = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.setRepeatCount(Animation.INFINITE);

        mValueAnimator2 = ValueAnimator.ofFloat(0.7f, 1.0f);
        mValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPaint2.setAlpha((int) (255 - 180 * animation.getAnimatedFraction()));
                mFactor = (float) animation.getAnimatedValue();
            }
        });
        mValueAnimator2.setRepeatCount(Animation.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3 * 1000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(mValueAnimator, mValueAnimator2);
        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(mBitmap3.getWidth(), mBitmap2.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(mBitmap3, 0, 0, mPaint3);
        canvas.restore();

        canvas.save();
        canvas.translate(getWidth() / 2 - mBitmap4.getWidth() / 2,
                    getHeight() / 2 - mBitmap4.getHeight() / 2);
        canvas.drawBitmap(mBitmap4, 0, 0, mPaint4);
        canvas.restore();

        canvas.save();
        canvas.translate(getWidth() / 2 - mBitmap1.getWidth() / 2,
                    getHeight() / 2 - mBitmap1.getHeight() / 2);

        matrix1.setRotate(mDegrees, mBitmap1.getWidth() / 2, mBitmap1.getHeight() / 2);
        canvas.drawBitmap(mBitmap1, matrix1, mPaint1);
        canvas.restore();

        canvas.translate(getWidth() / 2 - mBitmap2.getWidth() / 2,
                    getHeight() / 2 - mBitmap2.getHeight() / 2);
        matrix2.setScale(mFactor, mFactor, mBitmap2.getWidth() / 2, mBitmap2.getHeight() / 2);
        canvas.drawBitmap(mBitmap2, matrix2, mPaint2);
    }
}
