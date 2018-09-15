package com.github.propromarco.gismo.worker;

import com.github.propromarco.gismo.data.Radio;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.net.URL;

@Service
public class RadioService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Player mediafilePlayer;
    private Radio lastRadio;

    @Async
    public void switchOn(Radio radio) throws Exception {
        this.lastRadio = radio;
        switchOff(true);
        play(radio);
    }

    public synchronized void switchOff(boolean resetLastRadio) {
        if (mediafilePlayer != null) {
            mediafilePlayer.close();
            if (resetLastRadio) {
                lastRadio = null;
            }
        }
    }

    private void play(Radio radio) throws Exception {
        URL mediafile = new URL(radio.getUrl());
        InputStream stream = mediafile.openStream();
        mediafilePlayer = new Player(stream);
        mediafilePlayer.play();
    }

    public void setVolume(int percent) {
        FloatControl floatControl = findFloatControl();
        if (floatControl != null) {
            float minimum = floatControl.getMinimum();
            float maximum = floatControl.getMaximum();
            float fullDiff = maximum - minimum;
            float volumeDiff = minimum + (fullDiff * ((float) percent) / 100f);
            floatControl.setValue(volumeDiff);
        }
    }

    public int getVolume() {
        FloatControl floatControl = findFloatControl();
        if (floatControl != null) {
            float minimum = floatControl.getMinimum();
            float maximum = floatControl.getMaximum();
            float value = floatControl.getValue();
            float fullDiff = maximum - minimum;
            float volumeDiff = value - minimum;
            float percent = volumeDiff / fullDiff * 100f;
            return Math.round(percent);
        }
        return 0;
    }

    private FloatControl findFloatControl() {
        try {
            if (mediafilePlayer != null) {
                AudioDevice audioDevice = mediafilePlayer.getAudio();
                if (audioDevice instanceof JavaSoundAudioDevice) {
                    JavaSoundAudioDevice javaSoundAudioDevice = (JavaSoundAudioDevice) audioDevice;
                    SourceDataLine source = javaSoundAudioDevice.getSource();
                    if (source != null && source.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        FloatControl control = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                        log.info("Master min{} max{} vol{}", control.getMinimum(), control.getMaximum(), control.getValue());
                        return control;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    public Radio getLastRadio() {
        return lastRadio;
    }
}
