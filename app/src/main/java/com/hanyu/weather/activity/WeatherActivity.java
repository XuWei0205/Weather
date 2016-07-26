package com.hanyu.weather.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.test.UiThreadTest;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanyu.weather.R;
import com.hanyu.weather.util.HttpCallbackListener;
import com.hanyu.weather.util.HttpUtil;
import com.hanyu.weather.util.Utility;

import org.w3c.dom.Text;

/**
 * Created by Dell on 2016/7/26.
 */
public class WeatherActivity extends Activity {
    private LinearLayout weatherInfoLayout;

    /*显示城市名*/
    private TextView cityNameText;

    /*显示发布时间*/
    private TextView publishText;

    /*显示天气描述*/
    private TextView weatherDespText;

    /*显示温度1*/
    private TextView temp1text;

    /*显示温度2*/
    private TextView temp2text;

    /*显示当前时间*/
    private TextView currentDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);


        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1text = (TextView) findViewById(R.id.temp1);
        temp2text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_data);


        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)){
            publishText.setText("同步中。。。");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
            showWeather();
        }





    }



    private void queryWeatherCode(String countyCode){
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address,"countyCode");

    }



    private void queryFromServer(final String address, final String type ){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       publishText.setText("失败");
                   }
               });

            }
        });

    }
    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }


    private void showWeather(){


        SharedPreferences preferences = this.getPreferences(0);


        cityNameText.setText(preferences.getString("city_name" , ""));
        temp1text.setText(preferences.getString("temp1" , ""));
        temp2text.setText(preferences.getString("temp2" , ""));
        weatherDespText.setText(preferences.getString("weather_desp" , ""));
        publishText.setText(preferences.getString("publish_time" + "发布" , ""));
        currentDateText.setText(preferences.getString("current_data" , ""));


        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }
}
