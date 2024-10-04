    package com.riveo;

    import com.riveo.service.BisectService;
    import com.riveo.service.FibonachiService;
    import com.riveo.service.GoldenRatioService;

    public class Main {
        public static final double testFunction(final double x) {
            return x * (x - 1.0);
        }

        static final double LHS = -100;
        static final double RHS = 100;
        static final double EPS = 1e-6;
        static final int MAX_ITERATIONS = 1000;

        static BisectService bisectService = new BisectService();
        static GoldenRatioService goldenRatioService = new GoldenRatioService();
        static FibonachiService fibonachiService = new FibonachiService();

        public static void main(String[] args) {
            System.out.println(bisectService.getExtremum(Main::testFunction, LHS, RHS, EPS, MAX_ITERATIONS));
            System.out.println(goldenRatioService.getExtremum(Main::testFunction, LHS, RHS, EPS, MAX_ITERATIONS));
            System.out.println(fibonachiService.getExtremum(Main::testFunction, LHS, RHS, EPS));
        }
    }