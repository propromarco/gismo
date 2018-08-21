package com.github.propromarco.gismo.worker.service;

import com.github.propromarco.gismo.model.DecisionSubType;
import com.github.propromarco.gismo.services.VoiceService;
import marytts.exceptions.SynthesisException;

import java.text.MessageFormat;
import java.util.Date;

public class ServiceWorker {

    private final VoiceService service;
    private final MessageFormat pattern;

    public ServiceWorker(VoiceService service) {
        this.service = service;
        this.pattern = new MessageFormat("Es ist {0,date,HH} Uhr {0,date,mm}");
    }

    public void invoke(DecisionSubType subType) throws SynthesisException {
        switch (subType) {
            case Clock:
                Date now = new Date();
                String format = pattern.format(new Object[]{now});
                service.say(format);
                break;
        }
    }
}
