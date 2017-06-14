package cn.com.autohome.mycarapp.view.plus;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import cn.com.autohome.mycarapp.R;

/**
 * 作者/author：何清林（Administrator）
 * 时间/time：2017-2017/6/14-14:48
 * 邮箱/email：237607591@qq.com
 */
public class SuperTextView extends AppCompatTextView {
    private static final int DEFAULT_SOLID = Color.WHITE;
    private static final float DEFAULT_CORNER = 0f;

    private float density;
    private Paint paint;
    private int solid;
    private float corner;
    private Path solidPath;
    private RectF solidRectF;
    private float strokeWidth;
    private int width;
    private int height;
    private float leftTopCorner[] = new float[2];
    private float rightTopCorner[] = new float[2];
    private float leftBottomCorner[] = new float[2];
    private float rightBottomCorner[] = new float[2];
    private float corners[] = new float[8];
    private boolean leftTopCornerEnable;
    private boolean rightTopCornerEnable;
    private boolean leftBottomCornerEnable;
    private boolean rightBottomCornerEnable;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private float startLocation = -99999f;
    private Path firstLinePath = new Path();
    private float totalWidth = 50f;
    private float currentLocation = 0f;
    /**
     * 动画时长
     */
    private int animationLength = 3000;

    public SuperTextView(Context context) {
        super(context);
        init(null);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    getContext().obtainStyledAttributes(attrs, R.styleable.SuperTextView);
            solid = typedArray.getColor(R.styleable.SuperTextView_solid, DEFAULT_SOLID);
            corner = typedArray.getDimension(R.styleable.SuperTextView_corner, DEFAULT_CORNER);
        }
    }

    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    private void init(AttributeSet attrs) {
        density = getContext().getResources().getDisplayMetrics().density;
        initAttrs(attrs);
        paint = new Paint();
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        drawSolid(canvas);
        drawMoveLine(canvas);
        super.onDraw(canvas);
    }

    private void drawMoveLine(Canvas canvas) {
//        private Path secondLinePath = new Path();
        width = getWidth();
        height = getHeight();
//        if (startLocation == -99999f) {
//            startLocation = (float) (Math.random() * width);
//        }
//        if (startLocation < -(totalWidth * density + height * Math.tan(60))) {
//            startLocation = width;
//        }
        int firstLineWidth = (int) (totalWidth * density / 5 * 3);
        firstLinePath.reset();
        firstLinePath.moveTo(currentLocation, height);
        firstLinePath.lineTo(currentLocation + firstLineWidth, height);
        firstLinePath.lineTo((float) (currentLocation + firstLineWidth + height * Math.tan(60)), 0);
        firstLinePath.lineTo((float) (currentLocation + height * Math.tan(60)), 0);
        firstLinePath.close();
        paint.setXfermode(xfermode);
        paint.setColor(Color.parseColor("#4dffffff"));
        canvas.drawPath(firstLinePath, paint);
    }

    private void drawSolid(Canvas canvas) {
        if (solidPath == null) {
            solidPath = new Path();
        } else {
            solidPath.reset();
        }
        if (solidRectF == null) {
            solidRectF = new RectF();
        } else {
            solidRectF.setEmpty();
        }
        solidRectF.set(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth);
        getCorners(corner - strokeWidth / 2);
        solidPath.addRoundRect(solidRectF, corners, Path.Direction.CW);

        initPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(solid);
        canvas.drawPath(solidPath, paint);
    }

    private float[] getCorners(float corner) {
        leftTopCorner[0] = 0;
        leftTopCorner[1] = 0;
        rightTopCorner[0] = 0;
        rightTopCorner[1] = 0;
        leftBottomCorner[0] = 0;
        leftBottomCorner[1] = 0;
        rightBottomCorner[0] = 0;
        rightBottomCorner[1] = 0;
        if (this.leftTopCornerEnable || this.rightTopCornerEnable || this.leftBottomCornerEnable
                || this.rightBottomCornerEnable) {
            if (this.leftTopCornerEnable) {
                leftTopCorner[0] = corner;
                leftTopCorner[1] = corner;
            }
            if (this.rightTopCornerEnable) {
                rightTopCorner[0] = corner;
                rightTopCorner[1] = corner;
            }
            if (this.leftBottomCornerEnable) {
                leftBottomCorner[0] = corner;
                leftBottomCorner[1] = corner;
            }
            if (this.rightBottomCornerEnable) {
                rightBottomCorner[0] = corner;
                rightBottomCorner[1] = corner;
            }
        } else {
            leftTopCorner[0] = corner;
            leftTopCorner[1] = corner;
            rightTopCorner[0] = corner;
            rightTopCorner[1] = corner;
            leftBottomCorner[0] = corner;
            leftBottomCorner[1] = corner;
            rightBottomCorner[0] = corner;
            rightBottomCorner[1] = corner;
        }
        corners[0] = leftTopCorner[0];
        corners[1] = leftTopCorner[1];
        corners[2] = rightTopCorner[0];
        corners[3] = rightTopCorner[1];
        corners[4] = rightBottomCorner[0];
        corners[5] = rightBottomCorner[1];
        corners[6] = leftBottomCorner[0];
        corners[7] = leftBottomCorner[1];
        return corners;
    }

    /*** 开始属性动画      */
    public void startAnimation() {
        /**开始执行动画*/
        setAnimation(0, 1, animationLength);
    }

    private void setAnimation(float startLocation, float endLocation, int animationLength) {
        ValueAnimator processAnimator = ValueAnimator.ofFloat(startLocation, endLocation);
        processAnimator.setDuration(animationLength);
        processAnimator.setTarget(currentLocation);
        processAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentLocation = (float) animation.getAnimatedValue() * width;
                invalidate();
                Log.i("hql", "currentLocation:" + currentLocation);
            }
        });
        processAnimator.start();
    }

}
