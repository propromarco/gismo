package com.github.propromarco.gismo.ui;

import com.github.propromarco.gismo.controller.AudioRestController;
import com.github.propromarco.gismo.controller.RadioRESTController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ThymeleafController {

    private static final String INDEX_HTML = "/index.html";

    @Autowired
    private AudioRestController audioRestController;

    @Autowired
    private RadioRESTController radioRESTController;

    @RequestMapping("/")
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect(INDEX_HTML);
    }

    @RequestMapping(INDEX_HTML)
    public String index(HttpServletRequest request, Model model) throws Exception {
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
