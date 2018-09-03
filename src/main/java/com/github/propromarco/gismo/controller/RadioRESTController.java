package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.data.Radio;
import com.github.propromarco.gismo.worker.RadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RadioRESTController {

    @Autowired
    private RadioService radioService;

    @RequestMapping(value = "/einslive", method = RequestMethod.GET)
    public void einsLiveAn() {
        radioService.switchOn(Radio.EinsLive);
    }

    @RequestMapping(value = "/einslivediggi", method = RequestMethod.GET)
    public void einsLiveDiggiAn() {
        radioService.switchOn(Radio.EinsLiveDiggi);
    }

    @RequestMapping(value = "/aus", method = RequestMethod.GET)
    public void radioAus() {
        radioService.switchOff(true);
    }

    @RequestMapping(value = "/pause", method = RequestMethod.GET)
    public void radioPause() {
        radioService.switchOff(false);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public void radioResume() {
        Radio lastRadio = radioService.getLastRadio();
        if (lastRadio != null) {
            radioService.switchOn(lastRadio);
        }
    }

}
