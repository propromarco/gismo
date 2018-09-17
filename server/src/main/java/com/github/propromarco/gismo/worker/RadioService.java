package com.github.propromarco.gismo.worker;

import com.github.propromarco.gismo.data.Radio;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RadioService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Player> mediafilePlayers = new ArrayList<>();
    private Radio lastRadio;

    public RadioService() {
    }

    @Scheduled(fixedDelay = 2000)
    public void cleanup() {
        log.debug("Cleanup");
        while (mediafilePlayers.size() > 1) {
            Player player = mediafilePlayers.get(0);
            log.info("Cleaning up {}", player);
            player.close();
            mediafilePlayers.remove(player);
        }
    }

    @Async
    public void switchOn(Radio radio) throws Exception {
        this.lastRadio = radio;
        switchOff(true);
        play(radio);
    }

    public void switchOff(boolean resetLastRadio) {
        Player mediafilePlayer = geLastPlayerOrNull();
        if (mediafilePlayer != null) {
            mediafilePlayer.close();
            mediafilePlayers.remove(mediafilePlayer);
            mediafilePlayer = null;
            if (resetLastRadio) {
                lastRadio = null;
            }
        }
    }

    private void play(Radio radio) throws Exception {
        URL mediafile = new URL(radio.getUrl());
        InputStream stream = mediafile.openStream();
        Player mediafilePlayer = new Player(stream);
        mediafilePlayers.add(mediafilePlayer);
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
            Player mediafilePlayer = geLastPlayerOrNull();
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

    private Player geLastPlayerOrNull() {
        if (mediafilePlayers != null && !mediafilePlayers.isEmpty()) {
            int index = mediafilePlayers.size() - 1;
            return mediafilePlayers.get(index);
        } else {
            return null;
        }
    }

    public Radio getLastRadio() {
        return lastRadio;
    }
}
