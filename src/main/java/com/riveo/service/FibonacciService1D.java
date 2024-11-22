package com.riveo.service;

import com.riveo.Functional.IFunction1D;

public class FibonacciService1D {
    public static double getExtremum(IFunction1D func, double lhs, double rhs, final double eps) {
        if (lhs > rhs) {
            final double tmp = lhs;
            lhs = rhs;
            rhs = tmp;
        }

        double value, fb_t, fb_prev = 1.0, fb = 1.0;
        int iterations = 0;
        value = (rhs - lhs) / eps;

        while (fb < value) {
            iterations++;
            fb_t = fb_prev;
            fb_prev = fb;
            fb += fb_t;
        }

        double xl = lhs + (rhs - lhs) * ((fb - fb_prev) / fb);
        double xr = lhs + (rhs - lhs) * (fb_prev / fb);

        double fl = func.call(xl);
        double fr = func.call(xr);

        fb_t = fb - fb_prev;
        fb = fb_prev;
        fb_prev = fb_t;

        for (int i = iterations; i >= 0; i--) {
            if (fl > fr) {
                lhs = xl;
                fl = fr;
                xl = xr;
                xr = lhs + (rhs - lhs) * (fb_prev / fb);
                fr = func.call(xr);
            } else {
                rhs = xr;
                xr = xl;
                fr = fl;
                xl = lhs + (rhs - lhs) * ((fb - fb_prev) / fb);
                fl = func.call(xl);
            }
            fb_t = fb - fb_prev;
            fb = fb_prev;
            fb_prev = fb_t;
        }

        System.out.printf("fibonacci::function probes count : %s\n", 2 + iterations);
        System.out.printf("fibonacci::function arg range    : %s\n", rhs - lhs);

        return (rhs + lhs) * 0.5;
    }
}
