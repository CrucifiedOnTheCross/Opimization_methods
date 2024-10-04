package com.riveo.service;

import com.riveo.math.IFunction1D;

public class FibonachiService {
    public double getExtremum(IFunction1D func, double lhs, double rhs, final double eps) {
        if (lhs > rhs) {
            final double tmp = lhs;
            lhs = rhs;
            rhs = tmp;
        }

        double[] fibs = {1.0, 1.0};
        int iterations = calculateFibonacciIterations(rhs - lhs, eps, fibs);

        double xL = lhs + (rhs - lhs) * ((fibs[1] - fibs[0]) / fibs[1]);
        double xr = lhs + (rhs - lhs) * (fibs[0] / fibs[1]);

        double fl = func.call(xL);
        double fr = func.call(xr);

        updateFibonacciValues(fibs);

        for (int index = iterations; index > 0; index--) {
            if (fl > fr) {
                lhs = xL;
                fl = fr;
                xL = xr;
                xr = lhs + (rhs - lhs) * (fibs[0] / fibs[1]);
                fr = func.call(xr);
            } else {
                rhs = xr;
                xr = xL;
                fr = fl;
                xL = lhs + (rhs - lhs) * ((fibs[1] - fibs[0]) / fibs[1]);
                fl = func.call(xL);
            }
            updateFibonacciValues(fibs);
        }

        System.out.printf("BiSect::function probes count : %s\n", 2 + iterations);
        System.out.printf("BiSect::function arg range    : %s\n", rhs - lhs);

        return (rhs + lhs) * 0.5;
    }

    private int calculateFibonacciIterations(double range, double eps, double[] fibs) {
        int iterations = 0;
        double value = range / eps;
        while (fibs[1] < value) {
            iterations++;
            double fib_t = fibs[0];
            fibs[0] = fibs[1];
            fibs[1] += fib_t;
        }
        return iterations;
    }

    private void updateFibonacciValues(double[] fibs) {
        double fib_t = fibs[1] - fibs[0];
        fibs[1] = fibs[0];
        fibs[0] = fib_t;
    }
}
