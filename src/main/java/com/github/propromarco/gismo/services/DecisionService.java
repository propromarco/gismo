package com.github.propromarco.gismo.services;

import edu.cmu.sphinx.result.WordResult;

import java.util.List;

public interface DecisionService {
    DecisionResult makeDecision(String speechRecognitionResult, List<WordResult> words);
}
