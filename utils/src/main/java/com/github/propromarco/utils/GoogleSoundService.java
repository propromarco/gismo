package com.github.propromarco.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class GoogleSoundService {
    @Async
    public void playGoogle() throws JavaLayerException, IOException {
        try (InputStream is = getClass().getResourceAsStream("/google.mp3")) {
            Player player = new Player(is);
            player.play();
        }
    }

}
