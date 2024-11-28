package com.riveo;

import com.riveo.functional.IFunctionND;
import com.riveo.mathutils.DoubleVector;
import com.riveo.service.MultiDimensional;

public class NewtonRaphsonTest {

    public static void main(String[] args) {
        IFunctionND testFunction = x -> Math.pow(x.get(0) - 3, 2) + Math.pow(x.get(1) + 2, 2);

        DoubleVector startPoint = new DoubleVector(0.0, 0.0);  // Start at (0, 0)
        double epsilon = 1e-6;
        int maxIterations = 1000;

        System.out.println("Testing Newton-Raphson Method:");
        DoubleVector newtonResult = MultiDimensional.newtoneRaphson(testFunction, startPoint, epsilon, maxIterations);
        System.out.println("Newton-Raphson Result:" + newtonResult);
    }
}

