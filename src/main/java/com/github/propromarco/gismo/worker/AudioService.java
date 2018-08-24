package com.github.propromarco.gismo.worker;

import com.github.propromarco.gismo.data.VolumeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sound.sampled.LineUnavailableException;

@Service
public class AudioService {

    private static final int PERCENT_TO_INVOKE = 5;

    @Autowired
    private AudioSystemService audioSystemService;

    private float headphoneVolume;
    private float lineVolume;
    private float speakerVolume;

    public AudioService() {
    }

    @PostConstruct
    public void init() throws LineUnavailableException {
        this.headphoneVolume = audioSystemService.getHeadphoneVolume();
        this.lineVolume = audioSystemService.getLineVolume();
        this.speakerVolume = audioSystemService.getSpeakerVolume();
    }

    public void volumeUp(VolumeSystem volumeSystem) throws LineUnavailableException {
        int percent = getVolume(volumeSystem);
        percent += PERCENT_TO_INVOKE;
        if (percent >= 100) {
            percent = 100;
        }
        setVolume(volumeSystem, percent);
    }


    public void volumeDown(VolumeSystem volumeSystem) throws LineUnavailableException {
        int percent = getVolume(volumeSystem);
        percent -= PERCENT_TO_INVOKE;
        if (percent <= 0) {
            percent = 0;
        }
        setVolume(volumeSystem, percent);
    }

    public void setVolume(VolumeSystem volumeSystem, int percent) throws LineUnavailableException {
        float newVolume;
        switch (volumeSystem) {
            case LINE:
                newVolume = 1f * ((float) percent) / 100f;
                audioSystemService.setLineVolume(newVolume);
                init();
                break;
            case HEADPHONE:
                newVolume = 1f * ((float) percent) / 100f;
                audioSystemService.setHeadphoneVolume(newVolume);
                init();
                break;
            case SPEAKER:
                newVolume = 1f * ((float) percent) / 100f;
                audioSystemService.setSpeakerVolume(newVolume);
                init();
                break;
        }
    }

    public int getVolume(VolumeSystem volumeSystem) {
        switch (volumeSystem) {
            case LINE:
                return Math.round(lineVolume * 100f);
            case HEADPHONE:
                return Math.round(headphoneVolume * 100f);
            case SPEAKER:
                return Math.round(speakerVolume * 100f);
        }
        return 0;
    }

}
