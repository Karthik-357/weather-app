package com.weatherapp.weatherapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    @Value("${tomorrow.api.key}")
    private String apiKey;

    private static final String WEATHER_API_URL = "https://api.tomorrow.io/v4/timelines?location={city}&fields=temperature&timesteps=current&apikey=";

    @GetMapping("/home")
    public String home(@RequestParam(name = "city", required = false) String city, Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            // Get user's name and profile picture URL
            String userName = principal.getAttribute("name");  // Google profile name
            String profilePictureUrl = principal.getAttribute("picture"); // Google profile picture URL

            // Add user's name and profile picture to the model
            model.addAttribute("userName", userName);
            model.addAttribute("profilePictureUrl", profilePictureUrl);
        }

        if (city != null && !city.isEmpty()) {
            // Use RestTemplate to get weather data from Tomorrow.io API
            RestTemplate restTemplate = new RestTemplate();
            String url = WEATHER_API_URL + apiKey + "&city=" + city;
            String weatherData = restTemplate.getForObject(url, String.class);

            // Parse the weather data (for now, assuming static data)
            model.addAttribute("city", city);
            model.addAttribute("temperature", "25");  // Replace with actual data from weather API
            model.addAttribute("condition", "Sunny"); // Replace with actual data from weather API
        }

        return "home";
    }
}
