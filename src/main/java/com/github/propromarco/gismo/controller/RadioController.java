package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.data.Radio;
import com.github.propromarco.gismo.worker.RadioStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RadioController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RadioStreamer radioStreamer;

    @RequestMapping(value = "/einslinean", method = RequestMethod.GET)
    public void einsLiveAn() {
        radioStreamer.switchOn(Radio.EinsLive);
    }

    @RequestMapping(value = "/einsliveaus", method = RequestMethod.GET)
    public void einsLiveAus() {
        radioStreamer.switchOff();
    }

}
