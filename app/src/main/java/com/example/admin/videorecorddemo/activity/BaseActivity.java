package com.example.admin.videorecorddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.videorecorddemo.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by admin on 2018/6/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeSetContentView();
        setContentView(getLayoutById());
        ButterKnife.bind(this);
        initData();
        initEvent();
    }

    protected  void beforeSetContentView(){}


    protected void initEvent() {

    }

    protected abstract void initData();

    protected abstract int getLayoutById();
}