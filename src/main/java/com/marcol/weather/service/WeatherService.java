package com.marcol.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcol.weather.model.DailyData;
import com.marcol.weather.model.RequestInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface WeatherService {

    RequestInfo createRequestInfoItem(double latitude, double longitude, LocalDateTime time);
    List<LocalDate> retrieveDayDates(JsonNode nodeTime);
    List<LocalTime> retrieveSunTime(JsonNode nodeSun);
    List<Double> retrievePrecipitationSums(JsonNode nodePrecipitation);
    List<DailyData> createHistoricalData(List<LocalDate> dates, List<LocalTime> sunrises, List<LocalTime> sunsets, List<Double> precipitationSums);
}
