package com.riveo.mathutils;

import com.riveo.functional.IFunctionND;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPenaltyFunction implements IFunctionND {

    public enum MixMode {
        SUM,
        MUL,
        MAX,
        MIN
    }

    private final Set<IFunctionND> boundaries = new HashSet<>();
    private IFunctionND target;
    private MixMode mixMode;

    public AbstractPenaltyFunction(IFunctionND target, MixMode mixMode) {
        this.target = target;
        this.mixMode = mixMode != null ? mixMode : MixMode.SUM; // Значение по умолчанию SUM
    }

    public boolean addBoundary(IFunctionND boundary) {
        if (boundaries.contains(boundary))
            return false;
        boundaries.add(boundary);
        return true;
    }

    public boolean removeBoundary(IFunctionND boundary) {
        if (!boundaries.contains(boundary))
            return false;
        boundaries.remove(boundary);
        return true;
    }

    public boolean addBoundaries(IFunctionND... newBoundaries) {
        boolean modified = false;
        for (IFunctionND boundary : newBoundaries) {
            modified |= addBoundary(boundary);
        }
        return modified;
    }

    public boolean removeBoundaries(IFunctionND... newBoundaries) {
        boolean modified = false;
        for (IFunctionND boundary : newBoundaries) {
            modified |= removeBoundary(boundary);
        }
        return modified;
    }

    protected abstract double applyPenalty(IFunctionND boundary, DoubleVector arg);

    @Override
    public double call(DoubleVector arg) {
        double boundaryResult = switch (mixMode) {
            case MUL -> // Умножение
                    boundaries.stream()
                            .mapToDouble(boundary -> applyPenalty(boundary, arg))
                            .reduce(1.0, (result, penaltyValue) -> result * penaltyValue);
            case MAX -> // Максимум
                    boundaries.stream()
                            .mapToDouble(boundary -> applyPenalty(boundary, arg))
                            .reduce(Double.NEGATIVE_INFINITY, Math::max);
            case MIN -> // Минимум
                    boundaries.stream()
                            .mapToDouble(boundary -> applyPenalty(boundary, arg))
                            .reduce(Double.POSITIVE_INFINITY, Math::min);
            default -> // Сумма
                    boundaries.stream()
                            .mapToDouble(boundary -> applyPenalty(boundary, arg))
                            .sum();
        };

        return target == null ? boundaryResult : boundaryResult + target.call(arg);
    }
}
