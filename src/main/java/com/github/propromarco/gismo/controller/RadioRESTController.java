package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.data.Radio;
import com.github.propromarco.gismo.worker.RadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.LineUnavailableException;

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
    public void radioAus() throws LineUnavailableException {
        radioService.switchOff();
    }

}
