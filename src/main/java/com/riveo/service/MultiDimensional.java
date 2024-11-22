package com.riveo.service;

import com.riveo.functional.IFunctionND;
import com.riveo.mathUtils.DoubleMatrix;
import com.riveo.mathUtils.DoubleVector;
import com.riveo.mathUtils.NumericCommon;

import java.util.Objects;

public class MultiDimensional {
    public static DoubleVector biSect(IFunctionND function,
                                      DoubleVector left,
                                      DoubleVector right,
                                      double eps,
                                      int maxIterations) {
        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);
        DoubleVector xC, dir = DoubleVector.direction(lhs, rhs).mul(eps);

        int iteration = 0;
        while (iteration != maxIterations) {
            if (DoubleVector.sub(rhs, lhs).magnitude() < 2 * eps)
                break;
            xC = DoubleVector.add(rhs, lhs).mul(0.5);
            if (function.call(DoubleVector.add(xC, dir)) > function.call(DoubleVector.sub(xC, dir)))
                rhs = xC;
            else
                lhs = xC;

            ++iteration;
        }
        if (NumericCommon.SHOW_ZERO_ORDER_METHODS_DEBUG_LOG) {
            System.out.printf("biSect::function arg range    : %s\n", DoubleVector.sub(rhs, lhs).magnitude());
            System.out.printf("biSect::function probes count : %s\n", 2 * iteration);
        }
        return DoubleVector.add(rhs, lhs).mul(0.5);
    }

    public static DoubleVector biSect(IFunctionND function, DoubleVector left, DoubleVector right, double eps) {
        return biSect(function, left, right, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector biSect(IFunctionND function, DoubleVector left, DoubleVector right) {
        return biSect(function, left, right, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector goldenRatio(IFunctionND function,
                                           DoubleVector left,
                                           DoubleVector right,
                                           double eps,
                                           int maxIterations) {

        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);

        DoubleVector x_l = DoubleVector.sub(rhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
        DoubleVector x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));

        double f_l = function.call(x_l);
        double f_r = function.call(x_r);

        int iteration = 0;
        while ((iteration != maxIterations) && (DoubleVector.sub(rhs, lhs).magnitude() >= 2 * eps)) {
            if (f_l > f_r) {
                lhs = x_l;
                x_l = x_r;
                f_l = f_r;
                x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
                f_r = function.call(x_r);
            } else {
                rhs = x_r;
                x_r = x_l;
                f_r = f_l;
                x_l = DoubleVector.sub(rhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), NumericCommon.PSI));
                f_l = function.call(x_l);
            }

            ++iteration;
        }

        if (NumericCommon.SHOW_ZERO_ORDER_METHODS_DEBUG_LOG) {
            System.out.printf("goldenRatio::function arg range    : %s\n", DoubleVector.sub(rhs, lhs).magnitude());
            System.out.printf("goldenRatio::function probes count : %s\n", 2 + iteration);
        }

        return DoubleVector.add(rhs, lhs).mul(0.5);
    }

    public static DoubleVector goldenRatio(IFunctionND function, DoubleVector left, DoubleVector right, double eps) {
        return goldenRatio(function, left, right, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector goldenRatio(IFunctionND f, DoubleVector left, DoubleVector right) {
        return goldenRatio(f, left, right, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector fibonacci(IFunctionND function, DoubleVector left, DoubleVector right, double eps) {
        DoubleVector lhs = new DoubleVector(left);
        DoubleVector rhs = new DoubleVector(right);

        double value, fib_t, fib_1 = 1.0, fib_2 = 1.0;
        int iterations = 0;

        value = DoubleVector.sub(right, left).magnitude() / eps;
        while (fib_2 < value) {
            iterations++;
            fib_t = fib_1;
            fib_1 = fib_2;
            fib_2 += fib_t;
        }

        DoubleVector x_l = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), (fib_2 - fib_1) / fib_2));
        DoubleVector x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), fib_1 / fib_2));

        double f_l = function.call(x_l);
        double f_r = function.call(x_r);
        fib_t = fib_2 - fib_1;
        fib_2 = fib_1;
        fib_1 = fib_t;
        for (int index = iterations; index > 0; index--) {
            if (f_l > f_r) {
                lhs = x_l;
                x_l = x_r;
                f_l = f_r;
                x_r = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), fib_1 / fib_2));
                f_r = function.call(x_r);
            } else {
                rhs = x_r;
                x_r = x_l;
                f_r = f_l;
                x_l = DoubleVector.add(lhs, DoubleVector.mul(DoubleVector.sub(rhs, lhs), (fib_2 - fib_1) / fib_2));
                f_l = function.call(x_l);
            }
            fib_t = fib_2 - fib_1;
            fib_2 = fib_1;
            fib_1 = fib_t;
        }
        if (NumericCommon.SHOW_ZERO_ORDER_METHODS_DEBUG_LOG) {
            System.out.printf("goldenRatio::function arg range    : %s\n", DoubleVector.sub(rhs, lhs).magnitude());
            System.out.printf("goldenRatio::function probes count : %s\n", 2 + iterations);
        }

        return DoubleVector.add(rhs, lhs).mul(0.5);
    }

    public static DoubleVector fibonacci(IFunctionND function, DoubleVector left, DoubleVector right) {
        return fibonacci(function, left, right, NumericCommon.NUMERIC_ACCURACY_MIDDLE);
    }

    public static DoubleVector perCordDescend(IFunctionND function,
                                              DoubleVector xStart,
                                              double eps,
                                              int maxIterations) {
        double step = 1.0;
        double xi, y1, y0;

        DoubleVector x0 = new DoubleVector(xStart);
        DoubleVector x1 = new DoubleVector(xStart);

        int optCoordinatesCount = 0, coord_Id;
        int iteration = 0;

        while ((optCoordinatesCount != x1.size()) && (iteration < maxIterations)) {
            for (coord_Id = 0; coord_Id < x1.size(); coord_Id++) {
                iteration++;

                x1.set(coord_Id, x1.get(coord_Id) - eps);
                y0 = function.call(x1);
                x1.set(coord_Id, x1.get(coord_Id) + 2 * eps);
                y1 = function.call(x1);
                x1.set(coord_Id, x1.get(coord_Id) - eps);
                x1.set(coord_Id, y0 > y1 ? x1.get(coord_Id) + step : x1.get(coord_Id) - step);
                xi = x0.get(coord_Id);
                x1 = goldenRatio(function, x0, x1, eps, maxIterations);
                x0 = new DoubleVector(x1);

                optCoordinatesCount = Math.abs(x1.get(coord_Id) - xi) < 2 * eps ? optCoordinatesCount + 1 : 0;

                if (optCoordinatesCount == x1.size()) {
                    if (NumericCommon.SHOW_DEBUG_LOG)
                        System.out.printf("per cord descend iterations number : %s\n", iteration + 1);
                    break;
                }
            }
        }

        if (NumericCommon.SHOW_DEBUG_LOG)
            System.out.printf("per cord descend iterations number : %s\n", maxIterations);

        return x0;
    }

    public static DoubleVector perCordDescend(IFunctionND function, DoubleVector xStart, double eps) {
        return perCordDescend(function, xStart, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector perCordDescend(IFunctionND function, DoubleVector xStart) {
        return perCordDescend(function, xStart, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector gradientDescend(IFunctionND function, DoubleVector xStart, double eps, int maxIterations) {
        DoubleVector x_i = new DoubleVector(xStart);
        DoubleVector x_i_1 = new DoubleVector(xStart);
        int cnt = 0;
        for (; cnt != maxIterations; cnt++) {
            x_i_1 = DoubleVector.sub(x_i, DoubleVector.gradient(function, x_i, eps));
            x_i_1 = biSect(function, x_i, x_i_1, eps, maxIterations);
            if (DoubleVector.distance(x_i_1, x_i) < 2 * eps) break;
            x_i = x_i_1;
        }

        if (NumericCommon.SHOW_DEBUG_LOG) System.out.printf("gradient descend iterations number : %s\n", cnt + 1);

        return DoubleVector.add(x_i_1, x_i).mul(0.5);
    }

    public static DoubleVector gradientDescend(IFunctionND function, DoubleVector xStart, double eps) {
        return gradientDescend(function, xStart, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector gradientDescend(IFunctionND function, DoubleVector xStart) {
        return gradientDescend(function, xStart, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector conjGradientDescend(IFunctionND function, DoubleVector xStart, double eps, int maxIterations) {
        DoubleVector x_i = new DoubleVector(xStart);
        DoubleVector x_i_1 = new DoubleVector(xStart);
        DoubleVector s_i = DoubleVector.gradient(function, xStart, eps).mul(-1.0), s_i_1;
        double omega;
        int iteration = 0;
        for (; iteration != maxIterations; iteration++) {
            x_i_1 = DoubleVector.add(x_i, s_i);
            x_i_1 = biSect(function, x_i, x_i_1, eps, maxIterations);
            if (DoubleVector.distance(x_i_1, x_i) < 2 * eps) break;
            s_i_1 = DoubleVector.gradient(function, x_i_1, eps);
            omega = Math.pow((s_i_1).magnitude(), 2) / Math.pow((s_i).magnitude(), 2);
            s_i.mul(omega).sub(s_i_1);
            x_i = x_i_1;
        }

        if (NumericCommon.SHOW_DEBUG_LOG)
            System.out.printf("Conj gradient descend iterations number : %s\n", iteration + 1);
        return DoubleVector.add(x_i_1, x_i).mul(0.5);
    }

    public static DoubleVector conjGradientDescend(IFunctionND function, DoubleVector xStart, double eps) {
        return conjGradientDescend(function, xStart, eps, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector conjGradientDescend(IFunctionND function, DoubleVector xStart) {
        return conjGradientDescend(function, xStart, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_HIGH);
    }

    public static DoubleVector newtoneRaphson(IFunctionND function, DoubleVector xStart, double eps, int maxIterations) {
        DoubleVector x_i = new DoubleVector(xStart);
        DoubleVector x_i_1 = new DoubleVector(xStart);
        int iteration = 0;
        do {
            DoubleMatrix hess = Objects.requireNonNull(DoubleMatrix.invert(DoubleMatrix.hessian(function, x_i, eps)));
            x_i_1 = DoubleVector.sub(x_i, DoubleMatrix.mul(hess, DoubleVector.gradient(function, x_i, eps)));
            x_i = x_i_1;
            iteration++;
        } while (iteration != maxIterations && DoubleVector.sub(x_i_1, x_i).magnitude() >= eps);

        if (NumericCommon.SHOW_DEBUG_LOG)
            System.out.printf("Newtone - Raphson iterations number : %s\n", iteration + 1);
        return DoubleVector.add(x_i_1, x_i).mul(0.5);
    }

    public static DoubleVector newtoneRaphson(IFunctionND function, DoubleVector xStart, double eps) {
        return newtoneRaphson(function, xStart, eps, NumericCommon.ITERATIONS_COUNT_LOW);
    }

    public static DoubleVector newtoneRaphson(IFunctionND function, DoubleVector xStart) {
        return newtoneRaphson(function, xStart, NumericCommon.NUMERIC_ACCURACY_MIDDLE, NumericCommon.ITERATIONS_COUNT_LOW);
    }

}
