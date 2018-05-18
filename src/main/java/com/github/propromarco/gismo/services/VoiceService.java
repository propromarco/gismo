package com.github.propromarco.gismo.services;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioInputStream;
import java.util.Locale;

@Service
public class VoiceService {

    private final LocalMaryInterface marytts;
    private final AudioPlayer ap;

    public VoiceService() throws MaryConfigurationException {
        this.marytts = new LocalMaryInterface();
        // Set<String> availableVoices = marytts.getAvailableVoices(Locale.GERMAN);
        this.marytts.setLocale(Locale.GERMAN);
        this.ap = new AudioPlayer();
    }

    public void say(String text) throws SynthesisException {
        AudioInputStream audio = marytts.generateAudio(text);
        ap.setAudio(audio);
        ap.start();
    }

}
