package com.marcol.weather.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DailyData {

    private LocalDate date;
    private double precipitationAvg;
    private LocalTime sunrise;
    private LocalTime sunset;

    public DailyData(LocalDate date, double precipitationAvg, LocalTime sunrise, LocalTime sunset) {
        this.date = date;
        this.precipitationAvg = precipitationAvg;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrecipitationAvg() {
        return precipitationAvg;
    }

    public void setPrecipitationAvg(double precipitationAvg) {
        this.precipitationAvg = precipitationAvg;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }
}
