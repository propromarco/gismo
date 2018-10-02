package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jaxb.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherStationRestController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/news.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public News getNews() {
        News news = newsService.getNews();
        return news;
    }
}
