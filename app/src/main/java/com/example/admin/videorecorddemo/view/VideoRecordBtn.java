package com.example.admin.videorecorddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.videorecorddemo.R;

/**
 * Created by Administrator on 2018/6/10/010.
 */

public class VideoRecordBtn extends View {

    private int mInnerColor;
    private int mOutColor;
    private float mInnerRadius;
    private float mOutSize;
    private float mOutRadius;
    private Paint mInnerPaint;
    private Paint mOutPaint;
    private int mWidth;
    private boolean isDown;
    private OnViewPressedListener listener;

    public VideoRecordBtn(Context context) {
        this(context, null);
    }

    public VideoRecordBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoRecordBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray obtain = context.obtainStyledAttributes(attrs, R.styleable.VideoRecordBtn);
        mInnerColor = obtain.getColor(R.styleable.VideoRecordBtn_inner_circle_color, 0);
        mOutColor = obtain.getColor(R.styleable.VideoRecordBtn_out_circle_color, 0);
        mInnerRadius = obtain.getInteger(R.styleable.VideoRecordBtn_inner_circle_radius, 5);
        mOutSize = obtain.getInteger(R.styleable.VideoRecordBtn_out_circle_size, 2);
        obtain.recycle();

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setColor(0xffff0000);
        mOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutPaint.setStrokeWidth(mOutSize);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setColor(0xffffffff);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        if (mInnerRadius == 5) {
            mInnerRadius = mWidth * 3.0f / 10.0f;
        }
        if (mOutSize == 2) {
            mOutSize = mInnerRadius / 10f;
        }
        mOutRadius = mInnerRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2.0f, mWidth / 2.0f, mInnerRadius, mInnerPaint);
        canvas.drawCircle(mWidth / 2.0f, mWidth / 2.0f, mOutRadius, mOutPaint);
        if (mOutRadius > mInnerRadius) {
            postDelayed(onActionPressed, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (listener!=null) {
                    listener.onPressed(true);
                }
                isDown = true;
                postDelayed(onActionPressed, 0);
                break;
            case MotionEvent.ACTION_UP:
                if (listener!=null) {
                    listener.onPressed(false);
                }
                showBig = true;
                isDown = false;
                postDelayed(onActionPressed, 0);
                break;
        }
        return true;
    }

    private boolean showBig =true;
    private Runnable onActionPressed = new Runnable() {
        @Override
        public void run() {
            if (isDown) {
                if (mOutRadius <= mWidth / 2.0f - mOutSize&&showBig) {
                    mOutRadius++;
                    invalidate();
                } else if (mOutRadius > mWidth / 2.0f - mOutSize&&showBig) {
                    showBig = false;
                    invalidate();
                } else if (mOutRadius > (mWidth / 4.0f + mInnerRadius / 4.0f+mOutSize)&&!showBig) {
                    mOutRadius--;
                    invalidate();
                } else if (mOutRadius <= (mWidth / 4.0f + mInnerRadius / 4.0f+mOutSize)&&!showBig) {
                    showBig = true;
                    invalidate();
                }
            } else {
                if (mOutRadius > mInnerRadius) {
                    mOutRadius--;
                    invalidate();
                }
            }
        }
    };
    public interface OnViewPressedListener{
        void onPressed(boolean onPressed);
    }
    public void setOnViewPressedListener(OnViewPressedListener listener){
        this.listener = listener;
    }
}
