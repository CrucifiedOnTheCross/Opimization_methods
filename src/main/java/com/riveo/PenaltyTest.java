package com.riveo;

import com.riveo.functional.IFunctionND;
import com.riveo.mathutils.DoubleVector;
import com.riveo.mathutils.ExternalPenaltyFunction;
import com.riveo.mathutils.InternalPenaltyFunction;
import com.riveo.service.MultiDimensional;

public class PenaltyTest {

    public static void main(String[] args) {
        // Пример целевой функции (квадратичная функция)
        IFunctionND targetFunction = x -> DoubleVector.dot(DoubleVector.sub(x, 3.0), x);

        // Определение штрафных функций
        ExternalPenaltyFunction externalPenalty = new ExternalPenaltyFunction(targetFunction, 2); // MIX_MODE = 1 (умножение)
        InternalPenaltyFunction internalPenalty = new InternalPenaltyFunction(targetFunction, 2); // MIX_MODE = 1 (умножение)

        // Пример ограничения (boundary)
        IFunctionND boundary1 = new IFunctionND() {
            @Override
            public double call(DoubleVector arg) {
                double x = arg.get(0);
                return Math.max(0, x - 1);  // x >= 1
            }
        };

        externalPenalty.addBoundary(boundary1);
        internalPenalty.addBoundary(boundary1);

        DoubleVector startPoint = new DoubleVector(0.0, 0.0);

        DoubleVector resultTarget = MultiDimensional.conjGradientDescend(targetFunction, startPoint, 1e-6, 100);
        System.out.println("Результат с внешним штрафом: " + resultTarget);

        // Применение метода сопряженных градиентов с внешним штрафом
        DoubleVector resultExternalPenalty = MultiDimensional.conjGradientDescend(externalPenalty, startPoint, 1e-6, 100);
        System.out.println("Результат с внешним штрафом: " + resultExternalPenalty);

        // Применение метода сопряженных градиентов с внутренним штрафом
        DoubleVector resultInternalPenalty = MultiDimensional.conjGradientDescend(internalPenalty, startPoint, 1e-6, 100);
        System.out.println("Результат с внутренним штрафом: " + resultInternalPenalty);
    }
}
