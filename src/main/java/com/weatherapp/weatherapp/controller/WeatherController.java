package com.weatherapp.weatherapp.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WeatherController {

    @Value("${tomorrow.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Map<Integer, String> weatherDescriptions = new HashMap<>();
    private static final Map<Integer, String> weatherIcons = new HashMap<>();

    static {
        weatherDescriptions.put(1000, "Clear");
        weatherDescriptions.put(1100, "Mostly Clear");
        weatherDescriptions.put(1101, "Partly Cloudy");
        weatherDescriptions.put(1102, "Mostly Cloudy");
        weatherDescriptions.put(1001, "Cloudy");
        weatherDescriptions.put(2000, "Fog");
        weatherDescriptions.put(4000, "Drizzle");
        weatherDescriptions.put(4001, "Rain");
        weatherDescriptions.put(4200, "Light Rain");
        weatherDescriptions.put(4201, "Heavy Rain");
        weatherDescriptions.put(5000, "Snow");
        weatherDescriptions.put(5100, "Light Snow");
        weatherDescriptions.put(5101, "Heavy Snow");
        weatherDescriptions.put(6000, "Freezing Drizzle");
        weatherDescriptions.put(6001, "Freezing Rain");
        weatherDescriptions.put(6200, "Light Freezing Rain");
        weatherDescriptions.put(6201, "Heavy Freezing Rain");
        weatherDescriptions.put(8000, "Thunderstorm");

        weatherIcons.put(1000, "☀️");
        weatherIcons.put(1100, "🌤️");
        weatherIcons.put(1101, "⛅");
        weatherIcons.put(1102, "🌥️");
        weatherIcons.put(1001, "☁️");
        weatherIcons.put(2000, "🌫️");
        weatherIcons.put(4000, "🌦️");
        weatherIcons.put(4001, "🌧️");
        weatherIcons.put(4200, "🌦️");
        weatherIcons.put(4201, "🌧️");
        weatherIcons.put(5000, "❄️");
        weatherIcons.put(5100, "🌨️");
        weatherIcons.put(5101, "❄️");
        weatherIcons.put(6000, "🌧️");
        weatherIcons.put(6001, "🌧️");
        weatherIcons.put(6200, "🌧️");
        weatherIcons.put(6201, "🌧️");
        weatherIcons.put(8000, "⛈️");
    }

    @GetMapping("/weather/city")
    public String getWeatherByCity(@RequestParam String city, Model model) {
        String url = "https://api.tomorrow.io/v4/weather/realtime?location=" + city + "&apikey=" + apiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject json = new JSONObject(response.getBody());

        JSONObject data = json.getJSONObject("data");
        JSONObject values = data.getJSONObject("values");

        double temperature = values.getDouble("temperature");
        double humidity = values.getDouble("humidity");
        double windSpeed = values.getDouble("windSpeed");
        int weatherCode = values.optInt("weatherCode", 1000); // default to Clear

        String weatherDescription = weatherDescriptions.getOrDefault(weatherCode, "Unknown");
        String weatherIcon = weatherIcons.getOrDefault(weatherCode, "❓");

        model.addAttribute("city", city);
        model.addAttribute("temperature", temperature);
        model.addAttribute("humidity", humidity);
        model.addAttribute("windSpeed", windSpeed);
        model.addAttribute("weatherDescription", weatherDescription);
        model.addAttribute("weatherIcon", weatherIcon);

        return "weather";
    }
}