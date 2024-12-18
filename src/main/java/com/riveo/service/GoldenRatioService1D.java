package com.riveo.service;

import com.riveo.functional.IFunction1D;
import com.riveo.mathutils.NumericCommon;

public class GoldenRatioService1D {
    public static double getExtremum(IFunction1D func, double lhs, double rhs, final double epsilon, final int max_iter) {
        if (lhs > rhs) {
            double tmp = lhs;
            lhs = rhs;
            rhs = tmp;
        }

        int iteration = 0;
        double xl = rhs - (rhs - lhs) * NumericCommon.PSI;
        double xr = lhs + (rhs - lhs) * NumericCommon.PSI;
        double fl = func.call(xl);
        double fr = func.call(xr);

        while (iteration < max_iter && (rhs - lhs) >= 2 * epsilon) {
            if (fl > fr) {
                lhs = xl;
                xl = xr;
                fl = fr;
                xr = lhs + (rhs - lhs) * NumericCommon.PSI;
                fr = func.call(xr);
            } else {
                rhs = xr;
                xr = xl;
                fr = fl;
                xl = rhs - (rhs - lhs) * NumericCommon.PSI;
                fl = func.call(xl);
            }
            iteration++;
        }

        System.out.printf("Golden::function probes count : %s\n", 2 + iteration);
        System.out.printf("Golden::function arg range    : %s\n", rhs - lhs);

        return (rhs + lhs) * 0.5;
    }
}
