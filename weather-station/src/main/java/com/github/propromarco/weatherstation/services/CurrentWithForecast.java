package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.Current;
import com.github.propromarco.weatherstation.jabx.Forecast;

public class CurrentWithForecast {

    private final Current current;
    private final Forecast forecast;

    public CurrentWithForecast(Current current, Forecast forecast) {

        this.current = current;
        this.forecast = forecast;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }
}
