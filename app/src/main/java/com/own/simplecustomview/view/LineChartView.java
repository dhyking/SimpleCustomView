package com.own.simplecustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.own.simplecustomview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 折线图绘制
 * Created by dhy on 2017/1/18.
 */

public class LineChartView extends View {

    private int mWidth;                 //控件宽
    private int mHeight;                //控件高
    private Paint mPaint;
    private String showAmount;          //显示的金额
    private Rect mRect;
    private Paint mPaintAmount;           //金额
    private Paint mPaintPoint;            //圆点
    private Paint linePaint;               //线
    private Paint verticalPaint;            //竖线
    private float pointRadius = 10;              //圆点半径


    private float amountXSpacing;      //坐标X方向上间距
    private float startPositionX;       //开始X坐标位置
    private float[] xAlis = new float[7];
    private float[] yAlis = new float[7];
    private float item;
    private float tab;          //每个点X方向上距离
    private LinearGradient mLinearGradient; //渐变线
    private boolean isShowLine;
    private boolean isShowVertical;

    public LineChartView(Context context, float[] xArr, float[] yArr) {
        super(context);
        xAlis = xArr;
        yAlis = yArr;
        initPaint();
        initData();
    }

//
//    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//    }

    /**
     * 初始画笔
     */
    private void initPaint() {
        //文字画笔设置
        mPaintAmount = new Paint();
        mPaintAmount.setColor(Color.RED);
        mPaintAmount.setStrokeWidth(2);
        mPaintAmount.setAntiAlias(true);
        //圆点画笔画笔设置
        mPaintPoint = new Paint();
        mPaintPoint.setStrokeWidth(10);
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setColor(Color.BLUE);
        mPaintPoint.setAntiAlias(true); //抗锯齿化

        //折线画笔设置
        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.BLUE);
        linePaint.setAntiAlias(true); //抗锯齿化

        //竖线画笔设置
        mLinearGradient = new LinearGradient(0, 0, 100, 100, getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.white), Shader.TileMode.MIRROR);
        verticalPaint = new Paint();
        verticalPaint.setStrokeWidth(1);
        verticalPaint.setStyle(Paint.Style.FILL);
        verticalPaint.setColor(Color.GREEN);
        verticalPaint.setAntiAlias(true); //抗锯齿化
    }

    /**
     * 初始数据
     */
    private void initData() {
        item = getResources().getDimension(R.dimen.line_chart_item);
        tab = item * 3f;
        startPositionX = item;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int actWidth= 0;           //文字实际宽度
//        int actHeight = 0;          //文字实际高度
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            actWidth = widthSize;
//        } else {
////            mPaint.setTextSize(16);
////            mPaint.getTextBounds(showAmount, 0, showAmount.length(), mRect);
////            float textWidth = mRect.width();
////            int measureWidth = (int) (getPaddingLeft() + textWidth + getPaddingRight());
////            actWidth = measureWidth;
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            actHeight = heightSize;
//        } else {
////            mPaint.setTextSize(16f);
////            mPaint.getTextBounds(showAmount, 0, showAmount.length(), mRect);
////            float textHeight = mRect.height();
////            int measureHeight = (int) (getPaddingTop() + textHeight + getPaddingBottom());
////            actHeight = measureHeight;
//        }
//
//        Log.d("LineChartView", "actHeight:" + actHeight);
//        Log.d("LineChartView", "actWidth:" + actWidth);
//        setMeasuredDimension(actWidth, actHeight);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int j = 0; j < xAlis.length; j++) {
            Log.d("LineChartView", "xAlis[j]:" + xAlis[j]);
            Log.d("LineChartView", "yAlis[j]:" + yAlis[j]);
            drawCircle(canvas, j);
            drawLine(canvas, j);
            drawVertical(canvas, j);

        }
//        invalidate();
    }

    private void drawVertical(Canvas canvas, int index) {
        verticalPaint.setShader(mLinearGradient);
        canvas.drawLine(tab * index, getHeight(), tab * index, 0, verticalPaint);
    }

    /**
     * 画折线
     *
     * @param canvas
     * @param index
     */
    private void drawLine(Canvas canvas, int index) {
        if (index > 0) {
            canvas.drawLine(tab * (index - 1), getHeight() - yAlis[index - 1] * getHeight() - tab, tab * index,
                    getHeight() - yAlis[index] * getHeight() - tab, linePaint);
        }
    }

    /**
     * 画圆点
     *
     * @param canvas
     * @param index
     */
    private void drawCircle(Canvas canvas, int index) {
        canvas.drawCircle(tab * index, getHeight() - yAlis[index] * getHeight() - tab, pointRadius, mPaintPoint);
    }

    /**
     * 设置是否显示折线
     * @return
     */
    public void setShowLine(boolean show) {
        isShowLine = show;
    }

    /**
     * 设置是否显示竖线
     * @return
     */
    public void setShowVertical(boolean show) {
        isShowVertical = show;
    }
}
