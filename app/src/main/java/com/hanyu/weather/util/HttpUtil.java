package com.hanyu.weather.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dell on 2016/7/22.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);


                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("encoding","UTF-8");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);


                    InputStream in = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());

                    }


                }catch (Exception e){
                    Log.e("yichang",e.toString());
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }
}
