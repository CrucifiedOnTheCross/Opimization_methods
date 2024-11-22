package com.riveo.service;

import com.riveo.functional.IFunction1D;

public class BisectService1D {

    public static double getExtremum(IFunction1D func, double lhs, double rhs, final double epsilon, final int max_iter) {
        if (lhs > rhs) {
            double tmp = lhs;
            lhs = rhs;
            rhs = tmp;
        }

        int iteration = 0;
        while (iteration < max_iter && (rhs - lhs) >= 2 * epsilon) {
            double xc = (rhs + lhs) * 0.5;
            if (func.call(xc - epsilon) > func.call(xc + epsilon)) {
                lhs = xc;
            } else {
                rhs = xc;
            }
            iteration++;
        }

        System.out.printf("BiSect::function probes count : %s\n", 2 * iteration);
        System.out.printf("BiSect::function arg range    : %s\n", rhs - lhs);

        return (lhs + rhs) * 0.5;
    }

    public static double getExtremum(IFunction1D func, double lhs, double rhs) {
        return getExtremum(func, lhs, lhs, 1e-6, 10_000);
    }
}
