package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.worker.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioRestController {

    @Autowired
    private AudioService audioService;

    @RequestMapping(value = "/getvolume", method = RequestMethod.GET)
    public float getVolume() {
        return audioService.getVolume();
    }

    @RequestMapping(value = "/volumeup", method = RequestMethod.GET)
    public void volumeUp() {
        audioService.volumeUp();
    }

    @RequestMapping(value = "/volumedown", method = RequestMethod.GET)
    public void volumeDown() {
        audioService.volumeDown();
    }
}
