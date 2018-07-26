package com.example.admin.videorecorddemo.test;


import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by King on 2016/12/23.
 * 请求接口模型
 */

public class HttpPostRequest {
    private Context context = App.getInstance();
    private String mTAG="";
    private boolean yetRequest = false;

    public void requestPost(final String url, Map<String, String> params, final OnRequestListener listener, final int id) {
        final String TAG = url + params.toString();
        if(mTAG.equals(TAG)) {
            //如果还在请求中
            if(yetRequest) {
                //就返回，虽然封装的Volley请求里有判断TAG，先取消请求，但有些已经请求的就不会取消，导致请求好多条，结果返回过来，执行了好多次onSuccess
                return;
            }
        } else {
            //不是刚刚请求的地址，就记录这地址，准备下次比对
            mTAG = TAG;
        }
        //先设置已经发送请求，等结果出来再改成false
        yetRequest = true;
        BaseVolleyRequest.StringRequestPost(url, TAG, params, new BaseStrVolleyInterFace(BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(final String response) {
                App.getInstance().mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int ret = JsonObjectUtils.getJsonInt(jsonObject, "ret");
                            if (ret==1) {
                                listener.onSuccess(url, response, id);
                            } else {
                                listener.onFaild(url, response, id);
                                String message = JsonObjectUtils.getJsonString(jsonObject, "message");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            yetRequest = false;
                        }
                    }
                });
                yetRequest = false;
            }
            @Override
            public void onError(VolleyError volleyError) {
                listener.onFaild(url, "请检查您的网络状况", id);
                yetRequest = false;
            }
        });
    }
}

