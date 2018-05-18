package com.github.propromarco.gismo.coded;

import com.github.propromarco.gismo.services.DecisionResult;
import com.github.propromarco.gismo.services.DecisionService;
import edu.cmu.sphinx.result.WordResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodedDecisionService implements DecisionService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public DecisionResult makeDecision(String speechRecognitionResult, List<WordResult> words) {
        log.info("'{}' received", speechRecognitionResult);
        return null;
    }
}
