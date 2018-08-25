package com.github.propromarco.gismo.worker;

import com.github.propromarco.gismo.data.Radio;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;

@Service
public class RadioService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Thread radioThread = null;
    private Player mediafilePlayer;

    public void switchOn(Radio radio) {
        if (radioThread != null && radioThread.isAlive()) {
            radioThread.interrupt();
        }
        radioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    play(radio);
                } catch (Exception e) {
                    log.error("Fehler im Player", e);
                }
            }
        });
        radioThread.start();
    }

    public void switchOff() {
        radioThread.interrupt();
        mediafilePlayer.close();
    }

    private void play(Radio radio) throws Exception {
        URL mediafile = new URL(radio.getUrl());
        InputStream stream = mediafile.openStream();
        mediafilePlayer = new Player(stream);
        setVolume(50);
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
            Field audioField = Player.class.getDeclaredField("audio");
            audioField.setAccessible(true);
            if (mediafilePlayer != null) {
                AudioDevice audioDevice = (AudioDevice) audioField.get(mediafilePlayer);
                if (audioDevice instanceof JavaSoundAudioDevice) {
                    JavaSoundAudioDevice javaSoundAudioDevice = (JavaSoundAudioDevice) audioDevice;
                    Field sourceField = JavaSoundAudioDevice.class.getDeclaredField("source");
                    sourceField.setAccessible(true);
                    SourceDataLine source = (SourceDataLine) sourceField.get(javaSoundAudioDevice);
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

}
