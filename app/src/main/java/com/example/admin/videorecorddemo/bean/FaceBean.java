package com.example.admin.videorecorddemo.bean;

import com.qiniu.pili.droid.shortvideo.PLVideoFrame;

/**
 * Created by admin on 2018/6/11.
 */

public class FaceBean {
    public FaceBean(boolean isSelect, PLVideoFrame frame) {
        this.isSelect = isSelect;
        mFrame = frame;
    }

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public PLVideoFrame getFrame() {
        return mFrame;
    }

    public void setFrame(PLVideoFrame frame) {
        mFrame = frame;
    }

    private PLVideoFrame mFrame;
}
