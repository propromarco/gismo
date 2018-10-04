package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jaxb.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    public static final String TEMPLATE = "NewsService";
    private static final int SizeOfNews = 5;

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
        News news = entity.getBody();
        cleanUpTo(news, SizeOfNews);
        this.news = news;
    }

    private void cleanUpTo(News news, int sizeOfNews) {
        List<News.Articles> articles = news.getArticles();
        List<News.Articles> newArticles = new ArrayList<>();
        for (News.Articles article : articles) {
            String urlToImage = article.getUrlToImage();
            String description = article.getDescription();
            String title = article.getTitle();
            if (!StringUtils.isEmpty(urlToImage) && !StringUtils.isEmpty(description) && !StringUtils.isEmpty(title)) {
                int size = newArticles.size();
                if (size < sizeOfNews) {
                    newArticles.add(article);
                }
            }
        }
        news.setArticles(newArticles);
    }

    public News getNews() {
        return news;
    }
}
