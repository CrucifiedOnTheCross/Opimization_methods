package com.riveo;

import com.riveo.functional.IFunctionND;
import com.riveo.mathutils.DoubleVector;
import com.riveo.service.MultiDimensional;

public class MultiDimensionTest {

    public static void main(String[] args) {
        IFunctionND testFunction = x -> DoubleVector.dot(DoubleVector.sub(x, 1.0), x);

        DoubleVector left = new DoubleVector(-5.0, -5.0);
        DoubleVector right = new DoubleVector(5.0, 5.0);
        DoubleVector xStart = new DoubleVector(0.0, 0.0);

        double eps = 1e-5;

        int maxIterations = 1000;

        DoubleVector resultBiSect = MultiDimensional.biSect(testFunction, left, right, eps, maxIterations);
        System.out.println("biSect result: " + resultBiSect);

        DoubleVector resultGoldenRatio = MultiDimensional.goldenRatio(testFunction, left, right, eps, maxIterations);
        System.out.println("goldenRatio result: " + resultGoldenRatio);

        DoubleVector resultFibonacci = MultiDimensional.fibonacci(testFunction, left, right, eps);
        System.out.println("fibonacci result: " + resultFibonacci);

        DoubleVector resultPerCordDescend = MultiDimensional.perCordDescend(testFunction, xStart, eps, maxIterations);
        System.out.println("perCordDescend result: " + resultPerCordDescend);
    }

}
