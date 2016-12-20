package com.own.simplecustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.own.simplecustomview.R;
import com.own.simplecustomview.listener.OnTextChangeListener;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 基础自动定义文字的view
 * Created by dhy on 2016/12/15.
 */

public class CustomTextView extends View {

    private String titleText;           //文本内容
    private int titleColor;             //文本字体颜色
    private int titleSize;              //文本字体大小
    private Paint mPaint;               //文本画笔对象
    private Rect mRect;


    public CustomTextView(Context context) {
        this(context,null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性 (非必须,可不用)
        TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView,defStyleAttr,0);
        for (int mI = 0; mI < mTypedArray.getIndexCount(); mI++) {
            int attr = mTypedArray.getIndex(mI);
            switch (attr) {
                case R.styleable.CustomTextView_titleText:
                    titleText = mTypedArray.getString(attr);
                    break;
                case R.styleable.CustomTextView_titleColor:
                    titleColor = mTypedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTextView_titleSize:
                    //字体大小设置为sp
                    titleSize = mTypedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,0,getResources().getDisplayMetrics()));
                    break;
            }
        }

        mTypedArray.recycle();
        init();

    }

    /**
     * 初始化画笔
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(titleSize);
        mRect = new Rect();
        mPaint.getTextBounds(titleText,0,titleText.length(), mRect);
    }


    /**
     *根据适应文字长度，测量宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int actWidth;           //文字实际宽度
        int actHeight;          //文字实际高度
        if (widthMode == MeasureSpec.EXACTLY) {
            actWidth = widthSize;
        } else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText,0,titleText.length(),mRect);
            float textWidth = mRect.width();
            int measureWidth = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            actWidth = measureWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            actHeight = heightSize;
        } else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText,0,titleText.length(),mRect);
            float textHeight = mRect.height();
            int measureHeight = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            actHeight = measureHeight;
        }

        setMeasuredDimension(actWidth,actHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(titleColor);
        canvas.drawText(titleText,getWidth() / 2 - mRect.width() / 2,getHeight() /2 + mRect.height() / 2 ,mPaint);
    }

}
