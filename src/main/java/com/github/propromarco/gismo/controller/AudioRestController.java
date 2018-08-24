package com.github.propromarco.gismo.controller;

import com.github.propromarco.gismo.data.VolumeSystem;
import com.github.propromarco.gismo.worker.AudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.LineUnavailableException;

@RestController
public class AudioRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AudioService audioService;

    @RequestMapping(value = "/getvolume", method = RequestMethod.GET)
    public float getVolume(@RequestParam("type") VolumeSystem volumeSystem) throws LineUnavailableException {
        switch (volumeSystem) {
            case LINE:
                return audioService.getVolume(VolumeSystem.LINE);
            case SPEAKER:
                return audioService.getVolume(VolumeSystem.SPEAKER);
            case HEADPHONE:
                return audioService.getVolume(VolumeSystem.HEADPHONE);
            default:
                return -1;
        }
    }

    @RequestMapping(value = "/setvolume", method = RequestMethod.GET)
    public void setVolume(
            @RequestParam("type") VolumeSystem volumeSystem,
            @RequestParam("volume") int volume
    ) throws LineUnavailableException {
        audioService.setVolume(volumeSystem, volume);
    }

    @RequestMapping(value = "/volumeup", method = RequestMethod.GET)
    public void volumeUp(
            @RequestParam("type") VolumeSystem volumeSystem
    ) throws LineUnavailableException {
        audioService.volumeUp(volumeSystem);
    }

    @RequestMapping(value = "/volumedown", method = RequestMethod.GET)
    public void volumeDown(
            @RequestParam("type") VolumeSystem volumeSystem
    ) throws LineUnavailableException {
        audioService.volumeDown(volumeSystem);
    }
}
