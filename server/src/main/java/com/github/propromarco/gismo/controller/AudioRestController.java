package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.worker.AudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AudioService audioService;

    @RequestMapping(value = "/getvolume", method = RequestMethod.GET)
    public float getVolume() {
        int volume = audioService.getVolume();
        log.info("Get Volume with '{}'.", volume);
        return volume;
    }

    @RequestMapping(value = "/volumeup", method = RequestMethod.GET)
    public void volumeUp() {
        log.info("Volume up");
        audioService.volumeUp();
    }

    @RequestMapping(value = "/volumedown", method = RequestMethod.GET)
    public void volumeDown() {
        log.info("Volume down");
        audioService.volumeDown();
    }
}
