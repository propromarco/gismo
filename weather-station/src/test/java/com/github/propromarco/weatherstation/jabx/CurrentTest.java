package com.github.propromarco.weatherstation.jabx;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertNotNull;

public class CurrentTest {

    @Test
    public void testCallForecast() {
        TimeZone.setDefault(TimeZone.getTimeZone("Berlin"));
        CloseableHttpClient defaultHttpClient = HttpClientBuilder
                .create()
                .setDefaultHeaders(createDefaultHeader())
                .build();
        HttpComponentsClientHttpRequestFactory redirectsEnabledRequestFactory = new HttpComponentsClientHttpRequestFactory(defaultHttpClient);
        RestTemplate restTemplate = new RestTemplate(redirectsEnabledRequestFactory);
        ResponseEntity<Forecast> entity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/forecast?q=Troisdorf,de&lang=de&units=metric", Forecast.class);
        Forecast current = entity.getBody();
        assertNotNull(current);
    }

    @Test
    public void testCallCurrent() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        CloseableHttpClient defaultHttpClient = HttpClientBuilder
                .create()
                .setDefaultHeaders(createDefaultHeader())
                .build();
        HttpComponentsClientHttpRequestFactory redirectsEnabledRequestFactory = new HttpComponentsClientHttpRequestFactory(defaultHttpClient);
        RestTemplate restTemplate = new RestTemplate(redirectsEnabledRequestFactory);
        ResponseEntity<Current> entity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/weather?q=Troisdorf,de&lang=de&units=metric", Current.class);
        Current current = entity.getBody();
        assertNotNull(current);
        Sys sys = current.getSys();
    }

    protected List<BasicHeader> createDefaultHeader() {
        BasicHeader acceptCharsetHeader = new BasicHeader("x-api-key", "a6f6735c3e0c65046bbfe7f3d975d255");
        return Arrays.asList(acceptCharsetHeader);
    }


}
