package com.weatherapp.weatherapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${tomorrow.api.key}")
    private String apiKey;

    public String getWeatherByCity(String city) {
        String url = "https://api.tomorrow.io/v4/timelines?location=" + city + "&fields=temperature&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        return response;  // This will be the JSON response from the API
    }
}
