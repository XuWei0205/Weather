package com.hanyu.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hanyu.weather.service.AutoUpdateService;

/**
 * Created by Dell on 2016/7/28.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent (context, AutoUpdateService.class);
        context.startService(i);
    }

}
