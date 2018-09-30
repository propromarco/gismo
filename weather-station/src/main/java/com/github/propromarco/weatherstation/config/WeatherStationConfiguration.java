package com.github.propromarco.weatherstation.config;

import com.github.propromarco.weatherstation.services.NewsService;
import com.github.propromarco.weatherstation.services.OpenweathermapService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WeatherStationConfiguration {

    @Value("${appid}")
    private String appid;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    @Qualifier(OpenweathermapService.TEMPLATE)
    public RestTemplate createTemplate() {
        CloseableHttpClient defaultHttpClient = HttpClientBuilder
                .create()
                .setDefaultHeaders(createDefaultHeader())
                .build();
        HttpComponentsClientHttpRequestFactory redirectsEnabledRequestFactory = new HttpComponentsClientHttpRequestFactory(defaultHttpClient);
        RestTemplate restTemplate = new RestTemplate(redirectsEnabledRequestFactory);
        return restTemplate;
    }

    @Bean
    @Qualifier(NewsService.TEMPLATE)
    public RestTemplate createTemplate2() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    protected List<BasicHeader> createDefaultHeader() {
        BasicHeader acceptCharsetHeader = new BasicHeader("x-api-key", appid);
        return Arrays.asList(acceptCharsetHeader);
    }


}
