package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.data.Radio;
import com.github.propromarco.gismo.worker.RadioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RadioRESTController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RadioService radioService;

    @RequestMapping(value = "/einslive", method = RequestMethod.GET)
    public void einsLiveAn() throws Exception {
        log.info("Eins Live triggered");
        radioService.setLastRadio(Radio.EinsLive);
        radioService.switchOn(Radio.EinsLive);
    }

    @RequestMapping(value = "/einslivediggi", method = RequestMethod.GET)
    public void einsLiveDiggiAn() throws Exception {
        log.info("Eins Live Diggi triggered");
        radioService.setLastRadio(Radio.EinsLiveDiggi);
        radioService.switchOn(Radio.EinsLiveDiggi);
    }

    @RequestMapping(value = "/aus", method = RequestMethod.GET)
    public void radioAus() {
        log.info("Off triggered");
        radioService.setLastRadio(null);
        radioService.switchOff();
    }

    @RequestMapping(value = "/pause", method = RequestMethod.GET)
    public void radioPause() {
        log.info("Pause triggered");
        radioService.switchOff();
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public void radioResume() throws Exception {
        log.info("Resume triggered");
        Radio lastRadio = radioService.getLastRadio();
        if (lastRadio != null) {
            radioService.switchOn(lastRadio);
        }
    }

    @RequestMapping(value = "/google", method = RequestMethod.GET)
    public void google() throws Exception {
        log.info("Playing google sound");
        radioService.playGoogle();
    }

}
