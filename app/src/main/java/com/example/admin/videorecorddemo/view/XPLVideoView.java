package com.example.admin.videorecorddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.pili.pldroid.player.widget.PLVideoView;

/**
 * Created by admin on 2018/6/5.
 */

public class XPLVideoView extends PLVideoView {
    public XPLVideoView(Context context) {
        super(context);
    }

    public XPLVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XPLVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XPLVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
