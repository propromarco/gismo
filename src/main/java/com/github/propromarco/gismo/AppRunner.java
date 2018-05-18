package com.github.propromarco.gismo;

import com.github.propromarco.gismo.services.SpeechRecognizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private SpeechRecognizerService speechRecognizerService;

    @Override
    public void run(String... args) throws Exception {
        speechRecognizerService.startSpeechRecognition();
        speechRecognizerService.startResourcesThread();
    }
}
