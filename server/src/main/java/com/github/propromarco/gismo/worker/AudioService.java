package com.github.propromarco.gismo.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudioService {

    private static final int PERCENT_TO_INVOKE = 5;

    @Autowired
    private RadioService audioSystemService;

    public AudioService() {
    }

    public void volumeUp() {
        int percent = audioSystemService.getVolume();
        percent += PERCENT_TO_INVOKE;
        if (percent >= 100) {
            percent = 100;
        }
        audioSystemService.setVolume(percent);
    }


    public void volumeDown() {
        int percent = audioSystemService.getVolume();
        percent -= PERCENT_TO_INVOKE;
        if (percent <= 0) {
            percent = 0;
        }
        audioSystemService.setVolume(percent);
    }

    public int getVolume() {
        return audioSystemService.getVolume();
    }
}
