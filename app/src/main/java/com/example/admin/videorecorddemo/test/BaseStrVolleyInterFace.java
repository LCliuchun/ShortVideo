package com.example.admin.videorecorddemo.test;


import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * Created by King on 2016/12/11.
 * Volley封装好的接口
 */

public abstract class BaseStrVolleyInterFace {
	public static Response.Listener<String> mListener;
	public static ErrorListener mErrorListener;
	public BaseStrVolleyInterFace(Listener<String> listener, ErrorListener errorListener) {
		BaseStrVolleyInterFace.mErrorListener = errorListener;
		BaseStrVolleyInterFace.mListener = listener;
	}

	public abstract void onSuccess(String result);

	public abstract void onError(VolleyError error);

	public Listener<String> loadingListener() {
		mListener = new Listener<String>() {

			@Override
			public void onResponse(String result) {
				onSuccess(result);
			}
		};
		return mListener;
	}

	public ErrorListener errorListener() {
		mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				onError(error);
			}
		};
		return mErrorListener;

	}
}
