package com.example.admin.videorecorddemo.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.videorecorddemo.R;

/**
 * 导航栏
 */

public class TitleBar extends FrameLayout {

    private TextView tv_title;
    private OnRightButtonClickListener onRightButtonClickListener;
    private OnRightTextClickListener onRightTextClickListener;
    private OnLeftButtonClickListener onLeftButtonClickListener;
    private TextView tv_right;
    private ImageView iv_right;
    private ImageView mIv_left;
    private View mView;
    private View mLine;

    public TitleBar(@NonNull Context context) {
        this(context,null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mView = View.inflate(context, R.layout.title_bar, null);
        addView(mView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        boolean showLeftButton = typedArray.getBoolean(R.styleable.TitleBar_showLeftButton, true);
        String rightText = typedArray.getString(R.styleable.TitleBar_rightText);
        String title = typedArray.getString(R.styleable.TitleBar_title);
        int rightRes = typedArray.getResourceId(R.styleable.TitleBar_rightImage,0);
        int leftRes = typedArray.getResourceId(R.styleable.TitleBar_leftImage,0);
        int backRes = typedArray.getResourceId(R.styleable.TitleBar_titleBackground, 0);
        int themeColor = typedArray.getResourceId(R.styleable.TitleBar_themeColor, 0);
        typedArray.recycle();
        tv_title = (TextView) mView.findViewById(R.id.title);
        tv_right = (TextView) mView.findViewById(R.id.rightText);
        iv_right = (ImageView) mView.findViewById(R.id.rightImg);
        mIv_left = (ImageView) mView.findViewById(R.id.back);
        mLine=mView.findViewById(R.id.line);
        if (themeColor!=0) {
            tv_title.setTextColor(getResources().getColor(themeColor));
            tv_right.setTextColor(getResources().getColor(themeColor));
        }
        if (!showLeftButton) {
            mIv_left.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(rightText)) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(rightText);
        } else {
            tv_right.setVisibility(View.GONE);
        }
        tv_title.setText(title);
        if (backRes!=0) {
            mView.setBackgroundResource(backRes);
        }
        if (rightRes != 0) {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(rightRes);
        } else {
            iv_right.setVisibility(View.GONE);
        }

        if (leftRes != 0) {
            mIv_left.setVisibility(View.VISIBLE);
            mIv_left.setImageResource(leftRes);
        }
        mIv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftButtonClickListener!=null) {
                    onLeftButtonClickListener.onLeftButtonClick();
                }
            }
        });
        iv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightButtonClickListener!=null) {
                    onRightButtonClickListener.onRightButtonClick();
                }
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightTextClickListener!=null) {
                    onRightTextClickListener.onRightTextClick();
                }
            }
        });
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public TextView getRightText(){
        return tv_right;
    }
    public ImageView getRightView(){
        return iv_right;
    }

    public interface OnRightButtonClickListener{
        void onRightButtonClick();
    }
    public interface OnRightTextClickListener{
        void onRightTextClick();
    }
    public interface OnLeftButtonClickListener{
        void onLeftButtonClick();
    }

    public void setOnRightButtonClickListener(OnRightButtonClickListener onRightButtonClickListener){
        this.onRightButtonClickListener = onRightButtonClickListener;
    }
    public void setOnRightTextClickListener(OnRightTextClickListener onRightTextClickListener){
        this.onRightTextClickListener = onRightTextClickListener;
    }
    public void setOnLeftButtonClickListener(OnLeftButtonClickListener onLeftButtonClickListener){
        this.onLeftButtonClickListener = onLeftButtonClickListener;
    }
    public void setThemColor(int color){
        tv_title.setTextColor(color);
        tv_right.setTextColor(color);
    }

    public void setIvLeftImage(int resId) {
        mIv_left.setImageResource(resId);
    }

    public void setTitleBackGround(int color) {
        mView.setBackgroundResource(color);
    }

    public void setLineVisibility(int visibility) {
        if (mLine!=null) {
            mLine.setVisibility(visibility);
        }
    }
}
