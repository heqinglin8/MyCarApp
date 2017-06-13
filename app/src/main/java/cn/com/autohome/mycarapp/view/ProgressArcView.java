package cn.com.autohome.mycarapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

/**
 * 作者/author：何清林（Administrator） refer from panghongwei
 * 时间/time：2017-2017/6/13-17:03
 * 邮箱/email：237607591@qq.com
 */
public class ProgressArcView extends View {
    /**
     * 圆弧的宽度
     */
    private float borderWidth = 4f;
    /***
     * 内切圆padding
     */
    private float borderOffsex = 2f;
    /**
     * 默认颜色
     */
    private int color = Color.parseColor("#FA595C");
    /**
     * 进度底部圈颜色
     */
    private int fadeColor;
    /**
     * 进度圈实体颜色
     */
    private int solidColor;
    /**
     * 起点夹角
     */
    private int startAngle = -90;

    /**
     * 所要绘制的当前步数的红色圆弧终点到起点的夹角
     */
    private float currentAngle = 0;

    private float endAngle = 360;  //默认360度

    /**      * 动画时长      */
    private int animationLength = 1000;

    private String title = "全款参考";
    private String price = "41.68万";
    private String disc = "参考价：45.58万";

    public ProgressArcView(Context context) {
        super(context);
    }

    public ProgressArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** 指定本控件中心点 X 坐標 */
        float centerX = getWidth() / 2;
//        LogUtil.i("hql", " centerX:" + centerX);
        /** 指定画图的矩形区域 */
        RectF rectF = new RectF(0 + borderWidth + borderOffsex, 0 + borderWidth + borderOffsex, 2 * centerX - (borderWidth + borderOffsex), 2 * centerX - (borderWidth + borderOffsex));

        /** 淡色部分圆 */
        drawFadeCircle(canvas, rectF);
        /** 实心部分圆 */
        drawStrokeCircle(canvas, rectF);
        /** 画圆内部的数据 */
        drawInText(canvas, centerX);
    }


    private void drawInText(Canvas canvas, float centerX) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        //画第一行文字
        int textsize = 30;
        paint.setTextSize(textsize);
        canvas.drawText(title, 0 + centerX, 0 + centerX / 2 + textsize / 2, paint);

        //画第二行文字
        textsize = 50;
        paint.setTextSize(textsize);
        canvas.drawText(price, 0 + centerX, 0 + centerX + textsize / 2, paint);

        //画第三行文字
        textsize = 20;
        paint.setTextSize(textsize);
        canvas.drawText(disc, 0 + centerX, 0 + centerX + centerX / 2 + textsize / 2, paint);
    }

    private void drawFadeCircle(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        fadeColor = Color.argb(25, Color.red(color), Color.green(color), Color.blue(color));
        paint.setColor(fadeColor);
        //设置画笔宽度
        paint.setStrokeWidth(dipToPx(borderWidth));
        paint.setAntiAlias(true); //去除锯齿,顺滑
        paint.setStrokeJoin(Paint.Join.ROUND); //设置结合处为圆形
        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的样式圆形
        paint.setStyle(Paint.Style.STROKE);  //设置画笔的填充样式：FILL 是填充，STROKE是镂空
        canvas.drawArc(rectF, startAngle, 360, false, paint);
    }

    /**
     * 实心
     *
     * @param canvas
     * @param rectF
     */
    private void drawStrokeCircle(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        solidColor = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
        paint.setColor(solidColor);
        //设置画笔宽度
        paint.setStrokeWidth(dipToPx(borderWidth));
        paint.setAntiAlias(true); //去除锯齿
        paint.setStrokeJoin(Paint.Join.ROUND); //设置结合处为圆形
        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的样式圆形
        paint.setStyle(Paint.Style.STROKE);  //设置画笔的填充样式：FILL 是填充，STROKE是镂空
        canvas.drawArc(rectF, startAngle, currentAngle, false, paint);
    }

    /**
     * dip转换成px
     *
     * @param dip
     * @return
     */

    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = currentAngle;
        invalidate();
    }

    public void setProcess(float process){
        endAngle = 360 * process;
    }

    /*** 开始属性动画      */
    public void startAnimation() {
        /**开始执行动画*/
        setAnimation(0, endAngle, animationLength);
    }

    private void setAnimation(float startAngle, float endAngle, int animationLength) {
        ValueAnimator processAnimator = ValueAnimator.ofFloat(startAngle,endAngle);
        processAnimator.setDuration(animationLength);
        processAnimator.setTarget(currentAngle);
        processAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        processAnimator.start();
    }


}
