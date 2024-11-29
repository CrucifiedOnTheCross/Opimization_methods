package com.riveo.mathutils;

import com.riveo.functional.IFunctionND;

public class ExternalPenaltyFunction extends AbstractPenaltyFunction {

    public ExternalPenaltyFunction(IFunctionND target, MixMode mixMode) {
        super(target, mixMode);
    }

    public ExternalPenaltyFunction(IFunctionND target, int mixMode) {
        super(target, mixMode);
    }

    @Override
    protected double applyPenalty(IFunctionND boundary, DoubleVector arg) {
        return Math.pow(Math.max(0.0, boundary.call(arg)), 4);
    }

}
