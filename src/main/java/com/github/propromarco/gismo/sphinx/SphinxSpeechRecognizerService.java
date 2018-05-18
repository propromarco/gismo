package com.github.propromarco.gismo.sphinx;

import com.github.propromarco.gismo.services.DecisionResult;
import com.github.propromarco.gismo.services.DecisionService;
import com.github.propromarco.gismo.services.SpeechRecognizerService;
import com.github.propromarco.gismo.services.VoiceService;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SphinxSpeechRecognizerService implements SpeechRecognizerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DecisionService decisionService;

    @Autowired
    private VoiceService voiceService;

    private final LiveSpeechRecognizer recognizer;

    private boolean speechRecognizerThreadRunning;

    public SphinxSpeechRecognizerService() throws IOException {
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
    @Override
    public void startSpeechRecognition() {
        //locks
        speechRecognizerThreadRunning = true;

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


                //Check the result
                if (speechResult == null) {
                    log.info("I can't understand what you said.");
                } else {

                    //Get the hypothesis
                    String speechRecognitionResult = speechResult.getHypothesis();

                    //You said?
                    log.info("You said: [{}]", speechRecognitionResult);

                    //Call the appropriate method
                    DecisionResult answer = decisionService.makeDecision(speechRecognitionResult, speechResult.getWords());

                    voiceService.resolveDecision(answer);

                }

            }
        } catch (Exception ex) {
            log.warn(null, ex);
            speechRecognizerThreadRunning = false;
        }

        log.info("SpeechThread has exited...");
    }

}
