package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class WeatherStationController {

    private static final String INDEX_HTML = "/index.html";

    @Autowired
    private WeatherStationService weatherStationService;

    @Autowired
    private NewsService newsService;

    @RequestMapping("/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(INDEX_HTML);
    }

    @RequestMapping(INDEX_HTML)
    public String index(Model model) {
        List<CurrentWithForecast> all = weatherStationService.getAll();
        News news = newsService.getNews();
        model.addAttribute("all", all);
        model.addAttribute("news", news);
        model.addAttribute("helper", new Helper());
        return "index";
    }

}
