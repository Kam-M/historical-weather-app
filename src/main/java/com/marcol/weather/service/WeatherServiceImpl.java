package com.marcol.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.marcol.weather.model.DailyData;
import com.marcol.weather.model.RequestInfo;
import com.marcol.weather.repository.RequestInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RequestInfoRepository requestInfoRepository;

    @Override
    public RequestInfo createRequestInfoItem(double latitude, double longitude, LocalDateTime time) {
        RequestInfo requestInfo = new RequestInfo(latitude, longitude, time);
        return requestInfoRepository.save(requestInfo);
    }

    @Override
    public List<LocalDate> retrieveDayDates(JsonNode nodeTime) {
        return StreamSupport.stream(nodeTime.spliterator(), false)
                            .map(time -> time.asText())
                            .map(timeString -> LocalDate.parse(timeString))
                            .collect(Collectors.toList());
    }

    @Override
    public List<LocalTime> retrieveSunTime(JsonNode nodeSun) {
        return StreamSupport.stream(nodeSun.spliterator(), false)
                            .map(time -> time.asText())
                            .map(timeString -> LocalDateTime.parse(timeString))
                            .map(timeDate -> timeDate.toLocalTime())
                            .collect(Collectors.toList());
    }

    @Override
    public List<Double> retrievePrecipitationSums(JsonNode nodePrecipitation) {
        return StreamSupport.stream(nodePrecipitation.spliterator(), false)
                            .mapToDouble(p -> Double.parseDouble(p.asText()))
                            .boxed()
                            .collect(Collectors.toList());
    }

    @Override
    public List<DailyData> createHistoricalData(List<LocalDate> dates, List<LocalTime> sunrises, List<LocalTime> sunsets, List<Double> precipitationSums) {
        List<DailyData> dailyDataList = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            double precipitationAvg = getAveragePrecipitationForDayRounded(precipitationSums.get(i));
            DailyData dailyData = new DailyData(dates.get(i), precipitationAvg, sunrises.get(i), sunsets.get(i));
            dailyDataList.add(dailyData);
        }
        return dailyDataList;
    }

    private double getAveragePrecipitationForDayRounded(double precipitationSum){
        double precipitationAvg = precipitationSum / 24 ;
        return Math.round(precipitationAvg*100.0)/100.0;
    }
}