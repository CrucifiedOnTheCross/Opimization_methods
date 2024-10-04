package com.riveo.service;

import com.riveo.math.FibonacciPair;
import com.riveo.math.IFunction1D;

public class FibonachiService {
    public double getExtremum(IFunction1D func, double lhs, double rhs, final double eps) {
        if (lhs > rhs) {
            final double tmp = lhs;
            lhs = rhs;
            rhs = tmp;
        }

        FibonacciPair fibs = new FibonacciPair();
        int iterations = fibs.calculateFibonacciIterations((rhs - lhs) / eps);

        double xL = lhs + (rhs - lhs) * ((fibs.fb - fibs.fbPref) / fibs.fb);
        double xr = lhs + (rhs - lhs) * (fibs.fbPref / fibs.fb);

        double fl = func.call(xL);
        double fr = func.call(xr);

        fibs.updateFibonacciPair();

        for (int i = iterations; i > 0; i--) {
            if (fl > fr) {
                lhs = xL;
                fl = fr;
                xL = xr;
                xr = lhs + (rhs - lhs) * (fibs.fbPref / fibs.fb);
                fr = func.call(xr);
            } else {
                rhs = xr;
                xr = xL;
                fr = fl;
                xL = lhs + (rhs - lhs) * ((fibs.fb - fibs.fbPref) / fibs.fb);
                fl = func.call(xL);
            }
            fibs.updateFibonacciPair();
        }

        System.out.printf("Fibonachi::function probes count : %s\n", 2 + iterations);
        System.out.printf("Fibonachi::function arg range    : %s\n", rhs - lhs);

        return (rhs + lhs) * 0.5;
    }
}
