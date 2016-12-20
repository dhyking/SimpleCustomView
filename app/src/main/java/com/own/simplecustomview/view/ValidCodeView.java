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
 * 自定义验证码view
 * Created by dhy on 2016/12/15.
 */

public class ValidCodeView extends View {
    private int validCount;          //验证码位数
    private String validText;        //验证码内容
    private int validColor;          //验证码体颜色
    private int validSize;           //验证码字体大小
    private Paint mPaint;            //验证码画笔对象
    private Rect mRect;
    private OnTextChangeListener mOnTextChangeListener;         //文字改变监听
    private Random mRandom = new Random();
    private int[] sizeArr = {10,11,12,13,14,15,16,17,18,19,20}; //字体大小组
    private int[] colorArr = {Color.BLACK,Color.CYAN,Color.DKGRAY,
            Color.WHITE,Color.YELLOW,Color.GREEN};              //颜色组


    public ValidCodeView(Context context) {
        this(context,null);
    }

    public ValidCodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ValidCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ValidCodeView,defStyleAttr,0);
        for (int i = 0; i < mTypedArray.getIndexCount(); i++) {
            int attr = mTypedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ValidCodeView_validText:
                    validText = mTypedArray.getString(attr);
                    break;
                case R.styleable.ValidCodeView_validColor:
                    validColor = mTypedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ValidCodeView_validSize:
                    //字体大小设置为sp
                    validSize = mTypedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,0,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ValidCodeView_validCount:
                    validCount = mTypedArray.getInt(attr,0);
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
//        mPaint.setTextSize(validSize);
        mRect = new Rect();
//        mPaint.getTextBounds(validText,0,validText.length(), mRect);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                validText = randomText();
                if (mOnTextChangeListener != null) {
                    mOnTextChangeListener.onTextChanged(v,validText);
                }
                postInvalidate();
            }
        });
    }

    /**
     * 生成随机的数字
     * @return
     */
    private String randomText() {
        Set<Integer> set = new HashSet<>(validCount);
        while (set.size() < validCount) {
            int singleNum = mRandom.nextInt(10);
            set.add(singleNum);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer num : set) {
            sb.append(num + "");
        }
        return sb.toString();
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
            mPaint.setTextSize(validSize);
            mPaint.getTextBounds(validText,0,validText.length(),mRect);
            float textWidth = mRect.width();
            int measureWidth = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            actWidth = measureWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            actHeight = heightSize;
        } else {
            mPaint.setTextSize(validSize);
            mPaint.getTextBounds(validText,0,validText.length(),mRect);
            float textHeight = mRect.height();
            int measureHeight = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            actHeight = measureHeight;
        }

        setMeasuredDimension(actWidth,actHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextSize(validSize);
        validText = randomText();
        mPaint.getTextBounds(validText,0,validText.length(), mRect);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(validColor);
        canvas.drawText(validText,getWidth() / 2 - mRect.width() / 2,getHeight() /2 + mRect.height() / 2 ,mPaint);
        for (int i = 0;i < 5;i++) {
            randomStyleable(false);
            drawLine(canvas,mPaint);
        }

        for (int i = 0;i < 20;i++) {
            drawPoint(canvas,mPaint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
    }

    /**
     * 随机的样式
     * @param isContent 是否是验证码内容
     */
    private void randomStyleable(boolean isContent) {
        if (isContent) {
            int sizeIndex = mRandom.nextInt(sizeArr.length);
            mPaint.setTextSize(sizeArr[sizeIndex]);
        }
        int colorIndex = mRandom.nextInt(colorArr.length);
        mPaint.setColor(colorArr[colorIndex]);
    }

    /**
     * 画圆点
     * @param mCanvas
     * @param mPointPaint    圆点
     */
    private void drawPoint(Canvas mCanvas, Paint mPointPaint) {
        int x = mRandom.nextInt(getWidth());
        int y = mRandom.nextInt(getHeight());
        mCanvas.drawPoint(x,y,mPointPaint);
    }

    /**
     * 画线条
     * @param mCanvas
     * @param mLinePaint  线条
     */
    private void drawLine(Canvas mCanvas, Paint mLinePaint) {
        int startX = mRandom.nextInt(getWidth());
        int startY = mRandom.nextInt(getWidth());
        int endX = mRandom.nextInt(getHeight());
        int engY = mRandom.nextInt(getHeight());
        mCanvas.drawLine(startX,startY,endX,engY,mLinePaint);
    }

    /**
     * 获取到文本字符串
     * @return
     */
    public String getTitleText() {
        return validText;
    }

    /**
     * 设置文本改变监听
     * @param onTextChangeListener
     */
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.mOnTextChangeListener = onTextChangeListener;
    }
}
