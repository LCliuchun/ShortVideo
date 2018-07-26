package com.example.admin.videorecorddemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.SeekBar;

import com.example.admin.videorecorddemo.R;

public class AudioMixSettingDialog extends Dialog {

    private int mFgVolume = 100;
    private int mBgVolume = 100;

    private SeekBar mSrcVolumeSeekBar;
    private SeekBar mMixVolumeSeekBar;


    private OnAudioVolumeChangedListener mOnAudioVolumeChangedListener;

    public interface OnAudioVolumeChangedListener {
        void onAudioVolumeChanged(int fgVolume, int bgVolume);
    }

    public interface OnPositionSelectedListener {
        void onPositionSelected(long position);
    }

    public AudioMixSettingDialog(Context context){
        super(context,R.style.dialog2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_audio_mix);

        mSrcVolumeSeekBar = (SeekBar) findViewById(R.id.fg_volume);
        View cancelBtn = findViewById(R.id.cancel_btn);
        View commitBtn = findViewById(R.id.commit_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mSrcVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mFgVolume = progress;
                mOnAudioVolumeChangedListener.onAudioVolumeChanged(mFgVolume, mBgVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mMixVolumeSeekBar = (SeekBar) findViewById(R.id.bg_volume);
        mMixVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBgVolume = progress;
                mOnAudioVolumeChangedListener.onAudioVolumeChanged(mFgVolume, mBgVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

//        setBehaviorCallback();
    }

    public void setSrcVolumeProgress(int progress) {
        mSrcVolumeSeekBar.setProgress(progress);
    }

    public int getSrcVolumeProgress() {
        return mSrcVolumeSeekBar.getProgress();
    }

    public void setMixVolumeProgress(int progress) {
        mMixVolumeSeekBar.setProgress(progress);
    }

    public void setMixAudioMaxVolume(int maxVolume) {
        mMixVolumeSeekBar.setMax(maxVolume);
    }

    public void setOnAudioVolumeChangedListener(OnAudioVolumeChangedListener listener) {
        mOnAudioVolumeChangedListener = listener;
    }
}
