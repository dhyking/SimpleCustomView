package com.own.simplecustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
 * 折线统计图绘制
 * Created by dhy on 2017/1/18.
 */

public class LineChartView extends View {

    private String showAmount;          //显示的金额
    private Rect mRect;
    private Paint mPaintAmount;           //金额
    private Paint mPaintPoint;            //圆点
    private Paint linePaint;               //线
    private Paint verticalPaint;            //竖线
    private Paint weekPaint;                //星期
    private Paint datePaint;                //日期
    private float pointRadius = 10;              //圆点半径


    private float startPositionX;               //开始X坐标位置
    private float[] yAlis = new float[7];       //金额占比,可控制坐标Y方向高度
    private float[] amountArr = new float[7];   //金额集
    private String[] weekArr = new String[7];   //星期集
    private String[] dateArr = new String[7];   //日起集
    private float item;
    private float tab;          //每个点X方向上距离
    private LinearGradient mLinearGradient; //渐变线
    //默认是全画
    private boolean isShowLine = true;
    private boolean isShowVertical = true;
    private boolean isDrawAmount = true;
    private boolean isDrawDate = true;
    private boolean isDrawWeek = true;
    private float onePadding;           //坐标横向间距
    private float mVerticalPadding;     //文字和竖线的间距
    private float mHorizontalPadding;

    public LineChartView(Context context,float[] percent,float[] amount) {
        super(context);
        yAlis = percent;
        amountArr = amount;
        initPaint();
        initData();
    }

    /**
     * 初始画笔
     */
    private void initPaint() {

        mRect = new Rect();
        //文字画笔设置
        mPaintAmount = new Paint();
        mPaintAmount.setColor(Color.WHITE);
        mPaintAmount.setStrokeWidth(4);
        mPaintAmount.setTextSize(getResources().getDimension(R.dimen.text_mark));
        mPaintAmount.setAntiAlias(true);
        //圆点画笔画笔设置
        mPaintPoint = new Paint();
//        mPaintPoint.setStrokeWidth(10);
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setColor(Color.BLUE);
        mPaintPoint.setAntiAlias(true); //抗锯齿化

        //折线画笔设置
        linePaint = new Paint();
        linePaint.setStrokeWidth(3);
//        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(Color.WHITE);
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setAntiAlias(true); //抗锯齿化

        //竖线画笔设置
//        mLinearGradient = new LinearGradient(0, 0, 100, 100, getResources().getColor(R.color.white_light),
//                getResources().getColor(R.color.white), Shader.TileMode.MIRROR);
        verticalPaint = new Paint();
        verticalPaint.setStrokeWidth(1);
        verticalPaint.setStyle(Paint.Style.FILL);
        verticalPaint.setColor(Color.WHITE);
        verticalPaint.setAntiAlias(true); //抗锯齿化

        //星期画笔设置
        float weekSize = getResources().getDimension(R.dimen.line_week_size);
        weekPaint = new Paint();
        weekPaint.setStrokeWidth(5);
        weekPaint.setTextSize(weekSize);
        weekPaint.setStyle(Paint.Style.FILL);
        weekPaint.setColor(Color.BLACK);
        weekPaint.setAntiAlias(true); //抗锯齿化

        //日期画笔设置
        float dateSize = getResources().getDimension(R.dimen.line_date_size);
        datePaint = new Paint();
        datePaint.setTextSize(dateSize);
        datePaint.setStrokeWidth(5);
        datePaint.setStyle(Paint.Style.FILL);
        datePaint.setColor(Color.BLUE);
        datePaint.setAntiAlias(true); //抗锯齿化
    }

    /**
     * 初始数据
     */
    private void initData() {
        item = getResources().getDimension(R.dimen.line_chart_item);
        tab = item * 3f;
        weekArr = new String[]{"MON","MON","MON","MON","MON","MON","MON"};
        dateArr = new String[] {"12","13","14","15","16","17","18"};

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startPositionX = getWidth()/12;
        onePadding = (getWidth() - 2* startPositionX)/6;
        String data = dateArr[0]+"";
        datePaint.getTextBounds(data,0,data.length(),mRect);
        mVerticalPadding = mRect.height();
        mHorizontalPadding = mRect.width();

        for (int j = 0; j < amountArr.length; j++) {
            drawCircle(canvas, j);
            if (isShowLine) {
                drawLine(canvas, j);
            }
            if (isShowVertical) {
                drawVertical(canvas, j);
            }
            if (isDrawAmount) {
                drawAmount(canvas,j);
            }
            if (isDrawDate) {
                drawDate(canvas,j);
            }
            if (isDrawWeek) {
                drawWeek(canvas,j);
            }
        }
    }


    /**
     * 画竖线
     * @param canvas
     * @param index
     */
    private void drawVertical(Canvas canvas, int index) {
        canvas.drawLine(startPositionX + onePadding * index, getHeight(), startPositionX + onePadding * index, mVerticalPadding * 2f, verticalPaint);
    }

    /**
     * 画折线
     *
     * @param canvas
     * @param index
     */
    private void drawLine(Canvas canvas, int index) {
        if (index > 0) {
            canvas.drawLine( startPositionX + onePadding * (index - 1),
                    getHeight() - yAlis[index - 1] * getHeight() - tab,
                    startPositionX + onePadding * index, getHeight() - yAlis[index] * getHeight() - tab, linePaint);

        }
    }

    /**
     * 画圆点
     *
     * @param canvas
     * @param index
     */
    private void drawCircle(Canvas canvas, int index) {
        Log.d("LineChartView", "yAlis[index]:" + yAlis[index]);
        Log.d("LineChartView", "getHeight() - yAlis[index] * getHeight() - tab:" +
                (getHeight() - yAlis[index] * getHeight() - tab));
        canvas.drawCircle(startPositionX + onePadding * index,
                getHeight() - yAlis[index] * getHeight() - tab, pointRadius, mPaintPoint);
    }

    /**
     * 画金额
     * @param canvas
     * @param index
     */
    private void drawAmount(Canvas canvas, int index) {
        String data = amountArr[index]+"";
        mPaintAmount.getTextBounds(data,0,data.length(),mRect);
        float amountWidth =  mRect.width();
        float amountHeight = mRect.height();
        Log.d("LineChartView", "amountWidth:" + amountWidth+",amountHeight:"+amountHeight);
        canvas.drawText(data,startPositionX + onePadding * index - amountWidth /2,
                getHeight() - yAlis[index] * getHeight() - tab - 2*pointRadius,mPaintAmount);
    }

    /**
     * 画星期
     * @param canvas
     * @param index
     */
    private void drawWeek(Canvas canvas, int index) {
        String data = weekArr[index]+"";
        weekPaint.getTextBounds(data,0,data.length(),mRect);
        float weekWidth = mRect.width();
        float weekHeight = mRect.height();
        Log.d("LineChartView", "startPositionX + onePadding * index:" + (startPositionX + onePadding * index));
        canvas.drawText(data,startPositionX + onePadding * index,mVerticalPadding * 1.25f,weekPaint);
    }

    /**
     * 画日期
     * @param canvas
     * @param index
     */
    private void drawDate(Canvas canvas, int index) {
        String data = dateArr[index]+"";
        datePaint.getTextBounds(data,0,data.length(),mRect);
        float dateWidth = mRect.width();
        float dateHeight = mRect.height();
        canvas.drawText(data,startPositionX + onePadding * index - mHorizontalPadding,mVerticalPadding * 1.5f,datePaint);
    }

    /**
     * 设置是否画星期
     * @param mDrawWeek
     */
    public void setDrawWeek(boolean mDrawWeek) {
        isDrawWeek = mDrawWeek;
    }

    /**
     * 设置是否画日期
     * @param mDrawDate
     */
    public void setDrawDate(boolean mDrawDate) {
        isDrawDate = mDrawDate;
    }

    /**
     * 是否画金额
     * @param mShowAmount
     */
    public void setShowAmount(boolean mShowAmount) {
        isDrawAmount = mShowAmount;
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
