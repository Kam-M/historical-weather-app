package com.marcol.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcol.weather.model.DailyData;
import com.marcol.weather.model.RequestInfo;
import com.marcol.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @Value("${app.base.url}")
    private String baseURl;

    @Value("${app.past.days}")
    private String pastDays;

    @Value("${app.forecast.days}")
    private String forecastDays;

    @Value("${app.daily.info}")
    private String dailyInfo;

    @Value("${app.timezone}")
    private String timezone;

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String DAILY_NAME = "daily";
    public static final String PAST_DAYS_PARAM = "past_days";
    public static final String FORECAST_DAYS_PARAM = "forecast_days";
    public static final String TIMEZONE_PARAM = "timezone";
    public static final String TIME_NAME_JSON = "time";
    public static final String SUNRISE_NAME_JSON = "sunrise";
    public static final String SUNSET_NAME_JSON = "sunset";
    public static final String PRECIPITATION_NAME_JSON = "precipitation_sum";

    @GetMapping(produces = "application/json", path = "/weather/historical")
    @ResponseBody
    public ResponseEntity<List<DailyData>> getHistoricalWeatherData(@RequestParam String latitude, @RequestParam String longitude) {
        RequestInfo requestInfo = weatherService.createRequestInfoItem(Double.parseDouble(latitude), Double.parseDouble(longitude), LocalDateTime.now());

        String json = WebClient.create(baseURl)
                                .get()
                                .uri(uriBuilder -> uriBuilder
                                        .queryParam(LATITUDE, Double.parseDouble(latitude))
                                        .queryParam(LONGITUDE, Double.parseDouble(longitude))
                                        .queryParam(DAILY_NAME, dailyInfo)
                                        .queryParam(PAST_DAYS_PARAM, pastDays)
                                        .queryParam(FORECAST_DAYS_PARAM, forecastDays)
                                        .queryParam(TIMEZONE_PARAM, timezone)
                                        .build())
                                .retrieve()
                                .bodyToMono(String.class).block();
        if(json == null || json.isBlank()){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readValue(json, JsonNode.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
        JsonNode mainNode = jsonNode.get(DAILY_NAME);

        JsonNode singleNode = mainNode.get(TIME_NAME_JSON);
        List<LocalDate> dates = weatherService.retrieveDayDates(singleNode);

        singleNode = mainNode.get(SUNRISE_NAME_JSON);
        List<LocalTime> sunrises = weatherService.retrieveSunTime(singleNode);

        singleNode = mainNode.get(SUNSET_NAME_JSON);
        List<LocalTime> sunsets = weatherService.retrieveSunTime(singleNode);

        singleNode = mainNode.get(PRECIPITATION_NAME_JSON);
        List<Double> precipitationSums = weatherService.retrievePrecipitationSums(singleNode);

        List<DailyData> dailyDataList = weatherService.createHistoricalData(dates, sunrises, sunsets, precipitationSums);
        return new ResponseEntity<>(dailyDataList, HttpStatus.OK);
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }

    public String getBaseURl() {
        return baseURl;
    }

    public String getPastDays() {
        return pastDays;
    }

    public String getForecastDays() {
        return forecastDays;
    }

    public String getDailyInfo() {
        return dailyInfo;
    }

    public String getTimezone() {
        return timezone;
    }
}
