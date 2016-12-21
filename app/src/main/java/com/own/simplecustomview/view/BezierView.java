package com.own.simplecustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.own.simplecustomview.R;

/**
 * 自定义贝塞尔曲线
 * Created by dhy on 2016/12/21.
 */

public class BezierView extends View {
    private int bezierColor;    //曲线颜色
    private int bezierWidth;    //曲线线宽
    private Paint mPaint;
    private Path mPath;
    private Point startPoint;   //开始点
    private Point endPoint;     //结束点
    private Point assistPoint;   //辅助点
    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BezierView,defStyleAttr,0);
        for (int mI = 0; mI < mTypedArray.length(); mI++) {
            int attr = mTypedArray.getIndex(mI);
            switch (attr) {
                case R.styleable.BezierView_bezier_color:
                    bezierColor = mTypedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.BezierView_bezier_width:
                    bezierWidth = (int) mTypedArray.getDimension(attr,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            0,getResources().getDisplayMetrics()));
                    break;
            }
        }
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        startPoint = new Point(300,400);
        assistPoint = new Point(500,600);
        endPoint = new Point(600,800);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖动
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(bezierColor);
        mPaint.setStrokeWidth(bezierWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        //起点
        mPath.moveTo(startPoint.x,startPoint.y);
        mPath.reset();
        mPath.quadTo(assistPoint.x,assistPoint.y,endPoint.x,endPoint.y);
        //画路径
        canvas.drawPath(mPath,mPaint);
        //画辅助点
        canvas.drawPoint(assistPoint.x,assistPoint.y,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_HOVER_MOVE:
                assistPoint.x = (int) event.getX();
                assistPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
