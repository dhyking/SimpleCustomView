package com.own.simplecustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.own.simplecustomview.R;

/**
 * 自定义带圆角同时带有边界的view
 * Created by dhy on 2016/12/20.
 */

public class RoundImageView extends ImageView {
    private float borderWidth;              //边界宽度
    private int borderColor;                //颜色
    private float roundCornerX;             //x方向圆角
    private float roundCornerY;             //y方向圆角
    private Paint mPaint;                   //普通画笔
    private Paint mPaintCover;              //遮罩的画笔
    private Paint mPaintBorder;             //边界的画笔
    private Bitmap coverBitmap;             //遮罩图
    private Canvas coverCanvas;             //遮罩画布
    private Bitmap originBitmap;            //原图
    private Canvas originCanvas;            //原图画布
    private RectF mRoundRectClip;
    private RectF borderRect;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0);
        for (int mI = 0; mI < mTypedArray.length(); mI++) {
            int attr = mTypedArray.getIndex(mI);
            switch (attr) {
                //当边界宽度为0时图片不带边界线
                case R.styleable.RoundImageView_border_width:
                    borderWidth = mTypedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            0, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.RoundImageView_border_color:
                    borderColor = mTypedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.RoundImageView_corner_x:
                    roundCornerX = mTypedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            0, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.RoundImageView_corner_y:
                    roundCornerY = mTypedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            0, getResources().getDisplayMetrics()));
                    break;
            }
        }
        mTypedArray.recycle();
        init();
    }

    /**
     * 画笔初始化
     */
    private void init() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        if (mPaintCover == null) {
            mPaintCover = new Paint();
            mPaintCover.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mPaintCover.setAntiAlias(true);
        }
        if (mPaintBorder == null) {
            mPaintBorder = new Paint();
            mPaintBorder.setColor(borderColor);
            mPaintBorder.setStyle(Paint.Style.STROKE);
            mPaintBorder.setStrokeWidth(borderWidth);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (originBitmap == null) {
            coverBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            originBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            coverCanvas = new Canvas(coverBitmap);
            originCanvas = new Canvas(originBitmap);
        }
        //获取原图
        super.onDraw(originCanvas);
        if (mRoundRectClip == null) {
            mRoundRectClip = new RectF(borderWidth,borderWidth,getWidth() - borderWidth,getHeight() - borderWidth);
        }
        //绘制圆角矩形
        coverCanvas.drawRoundRect(mRoundRectClip,roundCornerX ,roundCornerY,mPaint);
        // 使用遮罩画笔扣除原图中的圆角矩形外面的部分
        originCanvas.drawBitmap(coverBitmap,0,0,mPaintCover);
        //创建border矩形
        if (borderRect == null) {
            borderRect =  new RectF(borderWidth / 2,borderWidth / 2,getWidth() - borderWidth / 2,getHeight() - borderWidth / 2);
        }
        //绘制border
        originCanvas.drawRoundRect(borderRect,roundCornerX,roundCornerY,mPaintBorder);
        canvas.drawBitmap(originBitmap,0,0,mPaint);
    }
}
