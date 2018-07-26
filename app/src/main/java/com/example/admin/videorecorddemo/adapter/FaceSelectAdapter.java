package com.example.admin.videorecorddemo.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.videorecorddemo.R;
import com.example.admin.videorecorddemo.bean.FaceBean;
import com.qiniu.pili.droid.shortvideo.PLVideoFrame;

import java.util.List;

/**
 * Created by admin on 2018/6/11.
 */

public class FaceSelectAdapter extends BaseQuickAdapter<FaceBean,BaseViewHolder> {
    public FaceSelectAdapter(int layoutResId, @Nullable List<FaceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FaceBean item) {
        ImageView faceImg = helper.getView(R.id.face_img);
        faceImg.setImageBitmap(item.getFrame().toBitmap());
        if (item.isSelect()) {
            faceImg.setBackgroundResource(R.mipmap.select_btn);
        } else {
            faceImg.setBackground(null);
        }
    }
}
