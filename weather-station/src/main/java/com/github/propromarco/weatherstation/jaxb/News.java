package com.github.propromarco.weatherstation.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class News {

    public static class Source {
        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ClassPojo [id = " + id + ", name = " + name + "]";
        }
    }

    public static class Articles {
        private Date publishedAt;

        private String author;

        private String urlToImage;

        private String title;

        private Source source;

        private String description;

        private String url;

        public Date getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(Date publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ClassPojo [publishedAt = " + publishedAt + ", author = " + author + ", urlToImage = " + urlToImage + ", title = " + title + ", source = " + source + ", description = " + description + ", url = " + url + "]";
        }
    }

    private List<Articles> articles = new ArrayList();

    private String totalResults;

    private String status;

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [articles = " + articles + ", totalResults = " + totalResults + ", status = " + status + "]";
    }
}
