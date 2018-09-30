package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.Current;
import com.github.propromarco.weatherstation.jabx.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenweathermapService {

    public static final String TEMPLATE = "OpenweathermapService";

    @Autowired
    @Qualifier(OpenweathermapService.TEMPLATE)
    private RestTemplate restTemplate;

    public Current getCurrect(String[] cityAndCountry) {
        String city, country;
        if (cityAndCountry.length == 1) {
            city = cityAndCountry[0];
            country = "de";
        } else {
            city = cityAndCountry[0];
            country = cityAndCountry[1];
        }
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&lang=de&units=metric";
        ResponseEntity<Current> entity = restTemplate.getForEntity(url, Current.class);
        Current current = entity.getBody();
        return current;
    }

    public Forecast getForecast(String[] cityAndCountry) {
        String city, country;
        if (cityAndCountry.length == 1) {
            city = cityAndCountry[0];
            country = "de";
        } else {
            city = cityAndCountry[0];
            country = cityAndCountry[1];
        }
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&lang=de&units=metric";
        ResponseEntity<Forecast> entity = restTemplate.getForEntity(url, Forecast.class);
        Forecast current = entity.getBody();
        return current;
    }

}
