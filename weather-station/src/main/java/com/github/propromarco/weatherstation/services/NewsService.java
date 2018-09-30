package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class NewsService {

    public static final String TEMPLATE = "NewsService";

    @Value("${newsid}")
    private String newsId;

    @Autowired
    @Qualifier(TEMPLATE)
    private RestTemplate restTemplate;
    private News news;

    @PostConstruct
    public void init() {
        loadNewsData();
    }

    @Scheduled(
            cron = "0 */15 * * * *"
    )
    public void loadNewsData() {
        String url = "https://newsapi.org/v2/top-headlines?country=de&apiKey=" + newsId;
        ResponseEntity<News> entity = restTemplate.getForEntity(url, News.class);
        this.news = entity.getBody();
    }

    public News getNews() {
        return news;
    }
}
