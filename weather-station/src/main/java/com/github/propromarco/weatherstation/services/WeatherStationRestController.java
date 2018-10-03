package com.github.propromarco.weatherstation.services;

import com.github.propromarco.utils.GoogleSoundService;
import com.github.propromarco.weatherstation.jaxb.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherStationRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewsService newsService;

    @Autowired
    private GoogleSoundService googleSoundService;

    @RequestMapping(value = "/news.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public News getNews() {
        News news = newsService.getNews();
        return news;
    }

    @RequestMapping(value = "/google", method = RequestMethod.GET)
    public void google() throws Exception {
        log.info("Playing google sound");
        googleSoundService.playGoogle();
    }

}
