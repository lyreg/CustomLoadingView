package com.uestc.lyreg.newton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.uestc.lyreg.loading.R;

/**
 * Created by Administrator on 2016/5/30.
 *
 * @Author lyreg
 */
public class CradleBall extends View {


    private int width;
    private int height;

    private Paint paint;

    private int defaultColor = Color.BLUE;

    private int loadingColor = defaultColor;

    // 直径
    private float ballSize = 50;

    public CradleBall(Context context) {
        super(context);
        initView(null);
    }


    public CradleBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    public CradleBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    /**
     * 初始化view, 使用自定义属性
     */
    private void initView(AttributeSet attrs) {
        if(attrs != null) {
            //在这里获取自定义属性
            TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.CradleBall);
            loadingColor = arr.getColor(R.styleable.CradleBall_cradle_ball_color, defaultColor);
            ballSize = arr.getDimension(R.styleable.CradleBall_cradle_ball_size, 50);
            arr.recycle();
        }

        // 初始化画笔，设置颜色、类型等
        paint = new Paint();
        paint.setColor(loadingColor);
        paint.setStyle(Paint.Style.FILL);
        // 防止边缘锯齿
        paint.setAntiAlias(true);
    }

//    // 由于只要关注小球的大小，而且在xml中指定就可以，
//    // 所以不需要重写onMeasure方法
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        width = w;
//        height = h;
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension((int)ballSize, (int)ballSize);
    }

    // 用canvas画一个圆
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(width/2, height/2, width/2, paint);
        canvas.drawCircle(ballSize/2, ballSize/2, ballSize/2, paint);
    }

    public void setLoadingColor(int color) {
        loadingColor = color;
        paint.setColor(color);
        postInvalidate();
    }

    public int getLoadingColor() {
        return loadingColor;
    }

    public void setBallSize(float size) {
        this.ballSize = size;
        postInvalidate();
    }
}
