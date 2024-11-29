package com.riveo.mathutils;

import com.riveo.functional.IFunctionND;

public class InternalPenaltyFunction extends AbstractPenaltyFunction {

    public InternalPenaltyFunction(IFunctionND target, MixMode mixMode) {
        super(target, mixMode);
    }

    public InternalPenaltyFunction(IFunctionND target, int mixMode) {
        super(target, mixMode);
    }

    @Override
    protected double applyPenalty(IFunctionND boundary, DoubleVector arg) {
        double boundaryValue = boundary.call(arg);
        return boundaryValue > 0 ? Math.pow(1.0 / boundaryValue, 2) : 0;
    }

}