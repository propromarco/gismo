package com.github.propromarco.gismo.services;

import com.github.propromarco.gismo.model.DecisionSubType;
import com.github.propromarco.gismo.model.DecisionType;

public class DecisionResult {

    private final DecisionType type;
    private final DecisionSubType subType;

    public DecisionResult(DecisionType type, DecisionSubType subType) {
        this.type = type;
        this.subType = subType;
    }

    public DecisionType getType() {
        return type;
    }

    public DecisionSubType getSubType() {
        return subType;
    }
}
