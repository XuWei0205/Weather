package com.hanyu.weather.util;

/**
 * Created by Dell on 2016/7/22.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
