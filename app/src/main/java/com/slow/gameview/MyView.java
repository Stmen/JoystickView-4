package com.slow.gameview;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View implements TimeAnimator.TimeListener {

    private static final float BUG_RADIUS_DP = 4f;
    private static final float BUG_TRAIL_DP = 200f;

    private Paint paint;
    private TimeAnimator animator;

    private float density;
    private int width, height;
    private PointF position;
    private PointF velocity;
//    private Path path;
//    private PathMeasure pathMeasure;

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        animator = new TimeAnimator();
        animator.setTimeListener(this);

        paint = new Paint();
        paint.setColor(Color.YELLOW);

        density = getResources().getDisplayMetrics().density;

//        path = new Path();
//        pathMeasure = new PathMeasure();
        position = new PointF();
        velocity = new PointF();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    public void onDetachedFromWindow() {
        animator.cancel();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        position.set(width / 2, height / 2);
//        path.rewind();
//        path.moveTo(position.x, position.y);
    }

    public void setVelocity(float vxDps, float vyDps) {
        velocity.set(vxDps * density, vyDps * density);
    }

    @Override
    public void onTimeUpdate(TimeAnimator timeAnimator, long l, long l1) {
        final float dt = l1 / 1000f; // seconds

        position.x += velocity.x * dt;
        position.y += velocity.y * dt;

        bound();

//        path.lineTo(position.x, position.y);

        invalidate();
    }

//    private void reflect() {
//        boolean flipX = false, flipY = false;
//        if (position.x > width) {
//            position.x = position.x - 2 * (position.x - width);
//            flipX = true;
//        } else if (position.x < 0) {
//            position.x = -position.x;
//            flipX = true;
//        }
//        if (position.y > height) {
//            position.y = position.y - 2 * (position.y - height);
//            flipY = true;
//        } else if (position.y < 0) {
//            position.y = -position.y;
//            flipY = true;
//        }
//        if (flipX) velocity.x *= -1;
//        if (flipY) velocity.y *= -1;
//    }


    private void bound() {
        if (position.x > width) {
            position.x = width;
        } else if (position.x < 0) {
            position.x = 0;
        }
        if (position.y > height) {
            position.y = height;
        } else if (position.y < 0) {
            position.y = 0;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawColor(Color.WHITE);

//        pathMeasure.setPath(path, false);
//        float length = pathMeasure.getLength();

//        if (length > BUG_TRAIL_DP * density) {
//            // Note - this is likely a poor way to accomplish the result. Just for demo purposes.
//            @SuppressLint("DrawAllocation")
//            PathEffect effect = new DashPathEffect(new float[]{length, length}, -length + BUG_TRAIL_DP * density);
//            paint.setPathEffect(effect);
//        }

        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(position.x, position.y, BUG_RADIUS_DP * density, paint);
    }
}
