package com.riveo.functional;

import com.riveo.mathutils.DoubleVector;

@FunctionalInterface
public interface IFunctionND {
    double call(DoubleVector arg);
}