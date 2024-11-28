package com.riveo.mathutils;

import com.riveo.functional.IFunctionND;

public class InternalPenaltyFunction extends AbstractPenaltyFunction {

    public InternalPenaltyFunction(IFunctionND target, MixMode mixMode) {
        super(target, mixMode);
    }

    @Override
    protected double applyPenalty(IFunctionND boundary, DoubleVector arg) {
        return Math.pow(1.0 / boundary.call(arg), 2);
    }

}