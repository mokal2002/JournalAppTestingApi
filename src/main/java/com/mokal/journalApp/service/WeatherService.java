package com.mokal.journalApp.service;


import com.mokal.journalApp.api.response.WeatherResponse;
import com.mokal.journalApp.cache.AppCache;
import com.mokal.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
//    private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;

    private AppCache appCache;

    private RedisService redisService;

    public WeatherService(AppCache appCache, RedisService redisService) {

        this.appCache = appCache;
        this.redisService = redisService;
    }


    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null){
            return weatherResponse;
        }else {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null){
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;

        }
    }

}
