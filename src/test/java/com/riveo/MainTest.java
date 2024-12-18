package com.riveo;

import com.riveo.service.BisectService1D;
import com.riveo.service.FibonacciService1D;
import com.riveo.service.GoldenRatioService1D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    public static double testFunction(final double x) {
        return x * (x - 1.0);
    }

    static final double LHS = -100;
    static final double RHS = 100;
    static final double EPS = 1e-6;
    static final int MAX_ITERATIONS = 1000;
    static final double RESULT = 0.5;

    @Test
    public void testBisectService() {
        BisectService1D bisectService = new BisectService1D();
        double result = bisectService.getExtremum(MainTest::testFunction, -100, 100, 1e-6, 1000);
        assertEquals(RESULT, result, EPS);
    }

    @Test
    public void testGoldenRatioService() {
        GoldenRatioService1D goldenRatioService = new GoldenRatioService1D();
        double result = goldenRatioService.getExtremum(MainTest::testFunction, -100, 100, 1e-6, 1000);
        assertEquals(RESULT, result, EPS);
    }

    @Test
    public void testFibonachiService() {
        FibonacciService1D fibonachiService = new FibonacciService1D();
        double result = fibonachiService.getExtremum(MainTest::testFunction, -100, 100, 1e-6);
        assertEquals(RESULT, result, EPS);
    }
}
