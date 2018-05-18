package com.github.propromarco.gismo.services;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import java.io.IOException;
import java.util.List;

@Service
public class SpeechRecognizerService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LiveSpeechRecognizer recognizer;
    private boolean resourcesThreadRunning;
    private boolean speechRecognizerThreadRunning;
    private boolean ignoreSpeechRecognitionResults;
    private String speechRecognitionResult;

    public SpeechRecognizerService() throws IOException {
        // Loading Message
        log.info("Loading Speech Recognizer...\n");

        // Configuration
        Configuration configuration = new Configuration();

        // Load model from the jar
        configuration.setAcousticModelPath("resource:/german_acoustic/");
        configuration.setDictionaryPath("resource:/german_lm/german.dic");

        //====================================================================================
        //=====================READ THIS!!!===============================================
        //Uncomment this line of code if you want the recognizer to recognize every word of the language
        //you are using , here it is English for example
        //====================================================================================
        configuration.setLanguageModelPath("resource:/german_lm/cmusphinx-voxforge-de.lm.bin");

        //====================================================================================
        //=====================READ THIS!!!===============================================
        //If you don't want to use a grammar file comment below 3 lines and uncomment the above line for language model
        //====================================================================================

        // Grammar
//        configuration.setGrammarPath("resource:/grammars");
//        configuration.setGrammarName("grammar");
//        configuration.setUseGrammar(true);

        recognizer = new LiveSpeechRecognizer(configuration);

    }

    @Async
    public void startSpeechRecognition() {
        //locks
        speechRecognizerThreadRunning = true;
        ignoreSpeechRecognitionResults = false;

        //Start Recognition
        recognizer.startRecognition(true);

        //Information
        log.info("You can start to speak...\n");

        try {
            while (speechRecognizerThreadRunning) {
                /*
                 * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
                 */
                SpeechResult speechResult = recognizer.getResult();

                //Check if we ignore the speech recognition results
                if (!ignoreSpeechRecognitionResults) {

                    //Check the result
                    if (speechResult == null)
                        log.info("I can't understand what you said.\n");
                    else {

                        //Get the hypothesis
                        speechRecognitionResult = speechResult.getHypothesis();

                        //You said?
                        System.out.println("You said: [" + speechRecognitionResult + "]\n");

                        //Call the appropriate method
                        makeDecision(speechRecognitionResult, speechResult.getWords());

                    }
                } else
                    log.info("Ingoring Speech Recognition Results...");

            }
        } catch (Exception ex) {
            log.warn(null, ex);
            speechRecognizerThreadRunning = false;
        }

        log.info("SpeechThread has exited...");
    }

    public void makeDecision(String speech, List<WordResult> words) {
        log.info("{} => {}", speech, words);
    }

    @Async
    public void startResourcesThread() {
        try {

            //Lock
            resourcesThreadRunning = true;

            // Detect if the microphone is available
            while (true) {

                //Is the Microphone Available
                if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
                    log.info("Microphone is not available.\n");

                // Sleep some period
                Thread.sleep(350);
            }

        } catch (InterruptedException ex) {
            log.warn(null, ex);
            resourcesThreadRunning = false;
        }
    }

}
