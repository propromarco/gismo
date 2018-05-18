package com.github.propromarco.gismo.marytts;

import com.github.propromarco.gismo.services.DecisionResult;
import com.github.propromarco.gismo.services.VoiceService;
import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import java.util.Locale;

@Service
public class MaryTTSVoiceService implements VoiceService {

    private final LocalMaryInterface marytts;
    private final AudioPlayer ap;

    public MaryTTSVoiceService() throws MaryConfigurationException {
        this.marytts = new LocalMaryInterface();
        // Set<String> availableVoices = marytts.getAvailableVoices(Locale.GERMAN);
        this.marytts.setLocale(Locale.GERMAN);
        this.ap = new AudioPlayer();
    }

    @Override
    public void resolveDecision(DecisionResult answer) {

    }

    public void say(String text) throws SynthesisException {
        AudioInputStream audio = marytts.generateAudio(text);
        ap.setAudio(audio);
        ap.start();
    }

}
