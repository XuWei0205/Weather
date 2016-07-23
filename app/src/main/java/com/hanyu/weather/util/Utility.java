package com.hanyu.weather.util;

import android.text.TextUtils;

import com.hanyu.weather.model.City;
import com.hanyu.weather.model.County;
import com.hanyu.weather.model.Province;
import com.hanyu.weather.model.WeatherDB;

/**
 * Created by Dell on 2016/7/23.
 */
public class Utility {
    public synchronized static boolean handleProvinceResponse(WeatherDB weatherDB,String response){
        if (!TextUtils.isEmpty(response)){
            String [] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length > 0){
                for(String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }




    public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            String [] allCity = response.split(",");
            if(allCity != null && allCity.length > 0){
                for(String c : allCity){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }



    public static boolean handleCountiesResponse(WeatherDB weatherDB,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String [] allCounties = response.split(",");
            if(allCounties != null && allCounties.length > 0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }

        return false;
    }


}
