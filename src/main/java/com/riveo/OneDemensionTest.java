package com.riveo;

import com.riveo.service.BisectService1D;
import com.riveo.service.FibonacciService1D;
import com.riveo.service.GoldenRatioService1D;

public class OneDemensionTest {
    static final double LHS = -10;
    static final double RHS = 10;
    static final double EPS = 1e-6;
    static final int MAX_ITERATIONS = 100;

    public static double testFunction(final double x) {
        return x * (x - 1.0);
    }

    public static void main(String[] args) {
        System.out.println(BisectService1D.getExtremum(OneDemensionTest::testFunction, LHS, RHS, EPS, MAX_ITERATIONS));
        System.out.println(GoldenRatioService1D.getExtremum(OneDemensionTest::testFunction, LHS, RHS, EPS, MAX_ITERATIONS));
        System.out.println(FibonacciService1D.getExtremum(OneDemensionTest::testFunction, LHS, RHS, EPS * 2));
    }
}