package com.riveo;

import com.riveo.functional.IFunctionND;
import com.riveo.mathUtils.DoubleVector;
import com.riveo.service.MultiDimensional;

public class GradientDescentTest {

    public static void main(String[] args) {
        IFunctionND testFunction = x -> Math.pow(x.get(0) - 3, 2) + Math.pow(x.get(1) + 2, 2);

        DoubleVector startPoint = new DoubleVector(0.0, 0.0);  // Start at (0, 0)
        double epsilon = 1e-6;
        int maxIterations = 1000;

        System.out.println("Testing Gradient Descent:");
        DoubleVector gradientResult = MultiDimensional.gradientDescend(testFunction, startPoint, epsilon, maxIterations);
        System.out.println("Gradient Descent Result:" + gradientResult);

        System.out.println("\nTesting Conjugate Gradient Descent:");
        DoubleVector conjugateGradientResult = MultiDimensional.conjGradientDescend(testFunction, startPoint, epsilon, maxIterations);
        System.out.println("Conjugate Gradient Descent Result:" + conjugateGradientResult);
    }
}
