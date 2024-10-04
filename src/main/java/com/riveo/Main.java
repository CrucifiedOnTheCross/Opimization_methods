package com.riveo;

import com.riveo.service.BisectService;
import com.riveo.service.FibonacciService;
import com.riveo.service.GoldenRatioService;

public class Main {
    public static double testFunction(final double x) {
        return x * (x - 1.0);
    }

    static final double LHS = -10;
    static final double RHS = 10;
    static final double EPS = 1e-4;
    static final int MAX_ITERATIONS = 100;

    static BisectService bisectService = new BisectService();
    static GoldenRatioService goldenRatioService = new GoldenRatioService();
    static FibonacciService fibonacciService = new FibonacciService();

    public static void main(String[] args) {
        System.out.println(bisectService.getExtremum(Main::testFunction, LHS, RHS, EPS, MAX_ITERATIONS));
        System.out.println(goldenRatioService.getExtremum(Main::testFunction, LHS, RHS, EPS, MAX_ITERATIONS));
        System.out.println(fibonacciService.getExtremum(Main::testFunction, LHS, RHS, EPS * 3));
    }
}