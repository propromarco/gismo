package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jaxb.ForecastEntry;
import com.github.propromarco.weatherstation.jaxb.Rain;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Helper {

    private final SimpleDateFormat dateFormatter;
    private final SimpleDateFormat timeFormatter;
    private final SimpleDateFormat jsonDateTimeFormatter;

    public Helper() {
        this.jsonDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateFormatter = new SimpleDateFormat("EE, dd.MM.yyyy", Locale.GERMANY);
        this.timeFormatter = new SimpleDateFormat("HH:mm", Locale.GERMANY);
    }

    public String formatDate(long d) {
        Date date = Date.from(Instant.ofEpochSecond(d));
        String formatted = dateFormatter.format(date);
        return formatted;
    }

    public String formatDate(Date date) {
        String formatted = dateFormatter.format(date);
        return formatted;
    }

    public String formatTime(long d) {
        Date date = Date.from(Instant.ofEpochSecond(d));
        String formatted = timeFormatter.format(date);
        return formatted;
    }

    public String formatTime(Date date) {
        String formatted = timeFormatter.format(date);
        return formatted;
    }

    public String formatTemp(float temp) {
        return Math.round(temp) + "°C";
    }

    public String formatPercent(float percent) {
        return Math.round(percent) + "%";
    }

    public List<String> getDates(List<ForecastEntry> data) {
        List<String> list = new ArrayList<>();
        for (ForecastEntry forecastWeatherData : data) {
            String s = formatDate(forecastWeatherData.getDt());
            if (!list.contains(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public List<ForecastEntry> getItems(List<ForecastEntry> data, String datum) {
        List<ForecastEntry> list = new ArrayList<>();
        for (ForecastEntry forecastWeatherData : data) {
            String s = formatDate(forecastWeatherData.getDt());
            if (datum.equals(s)) {
                list.add(forecastWeatherData);
            }
        }
        return list;
    }

    public int getColspan(List<ForecastEntry> data, String aktual) {
        int colspan = 0;
        for (ForecastEntry forecastWeatherData : data) {
            String s = formatDate(forecastWeatherData.getDt());
            if (s.equalsIgnoreCase(aktual)) {
                colspan++;
            }
        }
        return colspan;
    }

    public String formatRain(ForecastEntry forecastEntry) {
        Rain rain = forecastEntry.getRain();
        if (rain != null) {
            float h = rain.get3h();
            double rainAsDouble = Math.round(h * 10.0) / 10.0;
            if (rainAsDouble > 0.0) {
                return rainAsDouble + "mm";
            }
        }
        return "\u00A0";
    }

}
