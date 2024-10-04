package com.riveo.math;

public class FibonacciPair {
    public double fb;
    public double fbPref;

    public FibonacciPair() {
        this.fb = 1;
        this.fbPref = 1;
    }

    public int calculateFibonacciIterations(double value) {
        int iterations = 0;
        double tmp;
        while (fbPref < value) {
            tmp = this.fbPref;
            this.fbPref = this.fb;
            this.fb += tmp;
            iterations++;
        }
        return iterations;
    }

    public void shiftForwardFibonacciPair() {
        double tmp = this.fb - this.fbPref;
        this.fb = this.fbPref;
        this.fbPref = tmp;
    }
}
