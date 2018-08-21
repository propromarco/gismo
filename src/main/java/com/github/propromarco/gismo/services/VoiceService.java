package com.github.propromarco.gismo.services;

import marytts.exceptions.SynthesisException;

public interface VoiceService {
    void resolveDecision(DecisionResult answer) throws SynthesisException;

    void say(String text) throws SynthesisException;
}
