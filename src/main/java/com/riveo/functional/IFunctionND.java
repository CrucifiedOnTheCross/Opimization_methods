package com.riveo.functional;
import com.riveo.mathUtils.DoubleVector;

@FunctionalInterface
public interface IFunctionND{
    double call(DoubleVector arg);
}