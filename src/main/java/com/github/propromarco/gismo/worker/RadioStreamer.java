package com.github.propromarco.gismo.worker;

import com.github.propromarco.gismo.data.Radio;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class RadioStreamer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Thread radioThread = null;
    private Player mediafilePlayer;

    public void switchOn(Radio radio) {
        if (radioThread != null && radioThread.isAlive()) {
            radioThread.interrupt();
        }
        this.radioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    play(radio);
                } catch (IOException | JavaLayerException e) {
                    log.error("Fehler im Player", e);
                }
            }
        });
        this.radioThread.start();
    }

    public void switchOff() {
        this.radioThread.interrupt();
        mediafilePlayer.close();
    }

    private void play(Radio radio) throws IOException, JavaLayerException {
        URL mediafile = new URL(radio.getUrl());
        InputStream stream = mediafile.openStream();
        this.mediafilePlayer = new Player(stream);
        mediafilePlayer.play();
    }

}
