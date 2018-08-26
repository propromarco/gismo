package com.github.propromarco.gismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.LineUnavailableException;

@Controller
public class ThymeleafController {

    @Autowired
    private AudioRestController audioRestController;

    @Autowired
    private RadioRESTController radioRESTController;

    @RequestMapping(value = "/index.html")
    public String index(HttpServletRequest request, Model model) throws LineUnavailableException, InterruptedException {
        String radio = request.getParameter("radio");
        String line = request.getParameter("line");
        if (radio != null) {
            if ("aus".equalsIgnoreCase(radio)) {
                radioRESTController.radioAus();
            } else if ("EinsLive".equalsIgnoreCase(radio)) {
                radioRESTController.einsLiveAn();
                Thread.sleep(2000);
            } else if ("EinsLiveDiggi".equalsIgnoreCase(radio)) {
                radioRESTController.einsLiveDiggiAn();
                Thread.sleep(2000);
            }
        } else if (line != null) {
            if ("up".equalsIgnoreCase(line)) {
                audioRestController.volumeUp();
            } else {
                audioRestController.volumeDown();
            }
        }
        model.addAttribute("line", audioRestController.getVolume());
        return "index";
    }

}
