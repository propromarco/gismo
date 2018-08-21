package com.github.propromarco.gismo.coded;

import com.github.propromarco.gismo.model.DecisionSubType;
import com.github.propromarco.gismo.model.DecisionType;
import com.github.propromarco.gismo.services.DecisionResult;
import com.github.propromarco.gismo.services.DecisionService;
import com.github.propromarco.gismo.services.VoiceService;
import edu.cmu.sphinx.result.WordResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodedDecisionService implements DecisionService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoiceService voiceService;

    @Override
    public DecisionResult makeDecision(String speechRecognitionResult, List<WordResult> words) {
        log.info("'{}' received", speechRecognitionResult);
        if (speechRecognitionResult.contains("wie spät ist")) {
            return new DecisionResult(DecisionType.SERVICE, DecisionSubType.Clock);
        }
        return null;
    }
}
