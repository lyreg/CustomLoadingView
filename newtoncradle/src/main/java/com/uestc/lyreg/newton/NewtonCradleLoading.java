package com.uestc.lyreg.newton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.uestc.lyreg.loading.R;

/**
 * Created by Administrator on 2016/5/30.
 *
 * @Author lyreg
 */
public class NewtonCradleLoading extends LinearLayout {

    private CradleBall mCradleBallOne;
    private CradleBall mCradleBallTwo;
    private CradleBall mCradleBallThree;
    private CradleBall mCradleBallFour;
    private CradleBall mCradleBallFive;

    private static final int DURATION = 400;
    private static final int SHAKE_DISTANCE = 2;
    private static final float PIVOT_X = 0.5f;
    private static final float PIVOT_Y = -3f;
    private static final int DEGREE = 20;

    private boolean isStart = false;

    private int defaultColor = Color.BLUE;
    private int loadingColor = defaultColor;

    private float ballSize = 50;

    public NewtonCradleLoading(Context context) {
        super(context);
        initView(context, null);
    }

    public NewtonCradleLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public NewtonCradleLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.newton_cradle_loading, this, true);

        if(attrs != null) {
            //在这里获取自定义属性
            TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.NewtonCradleLoading);
            loadingColor = arr.getColor(R.styleable.NewtonCradleLoading_ball_color, defaultColor);
            ballSize = arr.getDimension(R.styleable.NewtonCradleLoading_ball_size, 50);
            arr.recycle();
        }
    }

    // inflate方法执行结束后执行这个方法
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mCradleBallOne      = (CradleBall) findViewById(R.id.ball_one);
        mCradleBallTwo      = (CradleBall) findViewById(R.id.ball_two);
        mCradleBallThree    = (CradleBall) findViewById(R.id.ball_three);
        mCradleBallFour     = (CradleBall) findViewById(R.id.ball_four);
        mCradleBallFive     = (CradleBall) findViewById(R.id.ball_five);

        setLoadingColor(loadingColor);
        setBallSize(ballSize);

        initAnim();
    }

    RotateAnimation rotateLeftAnimation;//旋转动画效果
    RotateAnimation rotateRightAnimation;
    TranslateAnimation shakeLeftAnimation;// 位移动画效果
    TranslateAnimation shakeRightAnimation;

    private void initAnim() {

        shakeLeftAnimation = new TranslateAnimation(0, SHAKE_DISTANCE, 0, 0);
        shakeLeftAnimation.setDuration(DURATION);
        shakeLeftAnimation.setInterpolator(new CycleInterpolator(2));

        shakeRightAnimation = new TranslateAnimation(0, -SHAKE_DISTANCE, 0, 0);
        shakeRightAnimation.setDuration(DURATION);
        shakeRightAnimation.setInterpolator(new CycleInterpolator(2));
//        shakeRightAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                if(isStart)
//                    startLeftAnim();
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });

        rotateLeftAnimation = new RotateAnimation(0, DEGREE,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_X,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateLeftAnimation.setRepeatCount(1);
        rotateLeftAnimation.setRepeatMode(Animation.REVERSE);
        rotateLeftAnimation.setDuration(DURATION);
        rotateLeftAnimation.setInterpolator(new DecelerateInterpolator());
        rotateLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isStart) {
                    mCradleBallTwo.startAnimation(shakeLeftAnimation);
                    mCradleBallThree.startAnimation(shakeLeftAnimation);
                    mCradleBallFour.startAnimation(shakeLeftAnimation);

                    mCradleBallFive.startAnimation(rotateRightAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        rotateRightAnimation = new RotateAnimation(0, -DEGREE,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_X,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_Y);
        rotateRightAnimation.setRepeatCount(1);
        rotateRightAnimation.setRepeatMode(Animation.REVERSE);
        rotateRightAnimation.setDuration(DURATION);
        rotateRightAnimation.setInterpolator(new DecelerateInterpolator());
        rotateRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isStart) {
//                    startRightAnim();
                    mCradleBallTwo.startAnimation(shakeRightAnimation);
                    mCradleBallThree.startAnimation(shakeRightAnimation);
                    mCradleBallFour.startAnimation(shakeRightAnimation);

                    mCradleBallOne.startAnimation(rotateLeftAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

//    private void startRightAnim() {
//        mCradleBallTwo.startAnimation(shakeRightAnimation);
//        mCradleBallThree.startAnimation(shakeRightAnimation);
//        mCradleBallFour.startAnimation(shakeRightAnimation);
//    }

    private void startLeftAnim() {
        mCradleBallOne.startAnimation(rotateLeftAnimation);
    }

    public void start() {
        if(!isStart) {
            isStart = true;
            startLeftAnim();
        }
    }

    public void stop() {
        isStart = false;
        mCradleBallOne.clearAnimation();
        mCradleBallTwo.clearAnimation();
        mCradleBallThree.clearAnimation();
        mCradleBallFour.clearAnimation();
        mCradleBallFive.clearAnimation();
    }

    public boolean isStart() {
        return isStart;
    }

    public void setLoadingColor(int color) {
        mCradleBallOne.setLoadingColor(color);
        mCradleBallTwo.setLoadingColor(color);
        mCradleBallThree.setLoadingColor(color);
        mCradleBallFour.setLoadingColor(color);
        mCradleBallFive.setLoadingColor(color);
    }

    public void setBallSize(float size) {
        mCradleBallOne.setBallSize(size);
        mCradleBallTwo.setBallSize(size);
        mCradleBallThree.setBallSize(size);
        mCradleBallFour.setBallSize(size);
        mCradleBallFive.setBallSize(size);
    }
}
