package com.riveo;

import com.riveo.functional.IFunctionND;
import com.riveo.mathutils.AbstractPenaltyFunction;
import com.riveo.mathutils.DoubleVector;
import com.riveo.mathutils.ExternalPenaltyFunction;
import com.riveo.mathutils.InternalPenaltyFunction;
import com.riveo.service.MultiDimensional;

import java.util.Arrays;
import java.util.List;

public class PenaltyTest {

    public static void main(String[] args) {
        IFunctionND targetFunction = x -> (x.get(0) - 5) * x.get(0) + (x.get(1) - 3) * x.get(1); // min at point x = 2.5, y = 1.5

        List<IFunctionND> boundaryFunctions = Arrays.asList(
                x -> x.get(1) - 5,
                x -> x.get(0) - 5,
                x -> -x.get(1) - 1,
                x -> -x.get(0) - 1
        );

        ExternalPenaltyFunction externalPenalty = new ExternalPenaltyFunction(targetFunction, 0);
        InternalPenaltyFunction internalPenalty = new InternalPenaltyFunction(targetFunction, 0);

        boundaryFunctions.forEach(externalPenalty::addBoundary);
        boundaryFunctions.forEach(internalPenalty::addBoundary);

        DoubleVector startPoint = new DoubleVector(-15., -35.);

        // Вывод результатов оптимизации целевой функции
        System.out.println("______RESULT TARGET FUNCTION______");
        printOptimizationResults(targetFunction, startPoint);

        // Вывод результатов оптимизации с внутренней штрафной функцией
        System.out.println("______RESULT INTERNAL PENALTY______");
        printOptimizationResults(internalPenalty, startPoint);

        // Вывод результатов оптимизации с внешней штрафной функцией
        System.out.println("______RESULT EXTERNAL PENALTY______");
        printOptimizationResults(externalPenalty, startPoint);
    }

    /**
     * Выполняет оптимизацию функции различными методами и выводит результаты.
     *
     * @param function   оптимизируемая функция
     * @param startPoint начальная точка
     */
    private static void printOptimizationResults(IFunctionND function, DoubleVector startPoint) {
        System.out.println("Gradient Descent: " + MultiDimensional.gradientDescend(function, startPoint, 1e-6, 100));
        System.out.println("Conjugate Gradient Descent: " + MultiDimensional.conjGradientDescend(function, startPoint, 1e-6, 100));
        System.out.println("Newton-Raphson: " + MultiDimensional.newtoneRaphson(function, startPoint, 1e-6, 100) + '\n');
    }
}
