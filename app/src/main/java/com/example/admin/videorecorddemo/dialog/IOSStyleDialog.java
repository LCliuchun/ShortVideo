package com.example.admin.videorecorddemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.admin.videorecorddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xianglei on 2018/3/19.
 */

public class IOSStyleDialog extends Dialog {

    @BindView(R.id.cancel_action)
    TextView mCancel;
    @BindView(R.id.determine)
    TextView mCommit;
    @BindView(R.id.message)
    TextView mMessage;
    private OnCommitClickListener mListener;

    public IOSStyleDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        View view = View.inflate(context, R.layout.dialog_logout, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(false);
    }

    public interface OnCommitClickListener{                                     //回调接口
        void onCommitClick();
    }
    public void setOnCommitClickListener(OnCommitClickListener listener){       //设置监听方法
        mListener = listener;
    }

    @OnClick({R.id.cancel_action, R.id.determine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_action:                    //取消键
                dismiss();
                break;
            case R.id.determine:                        //确认键
                if(mListener != null) {
                    mListener.onCommitClick();          //回调
                }
                break;
            default:
                break;
        }
    }
    public void setDialogFun(String message){
        mMessage.setText(message);
    }
}
