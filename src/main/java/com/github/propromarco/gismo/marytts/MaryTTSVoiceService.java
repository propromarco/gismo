package com.github.propromarco.gismo.marytts;

import com.github.propromarco.gismo.model.DecisionType;
import com.github.propromarco.gismo.services.DecisionResult;
import com.github.propromarco.gismo.services.VoiceService;
import com.github.propromarco.gismo.worker.service.ServiceWorker;
import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import java.util.Locale;
import java.util.Set;

@Service
public class MaryTTSVoiceService implements VoiceService {

    private final LocalMaryInterface marytts;

    public MaryTTSVoiceService() throws MaryConfigurationException {
        this.marytts = new LocalMaryInterface();
        Set<String> availableVoices = marytts.getAvailableVoices(Locale.GERMAN);
        this.marytts.setLocale(Locale.GERMAN);
        // this.marytts.setVoice("bits1-hsmm");
    }

    @Override
    public void resolveDecision(DecisionResult answer) throws SynthesisException {
        if (answer != null) {
            DecisionType type = answer.getType();
            switch (type) {
                case SERVICE:
                    ServiceWorker serviceWorker = new ServiceWorker(this);
                    serviceWorker.invoke(answer.getSubType());
                    break;
            }
        }
    }

    @Override
    public void say(String text) throws SynthesisException {
        AudioPlayer ap = new AudioPlayer();
        AudioInputStream audio = marytts.generateAudio(text);
        ap.setAudio(audio);
        ap.start();
    }

}
