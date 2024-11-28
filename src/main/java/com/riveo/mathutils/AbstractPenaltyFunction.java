package com.riveo.mathutils;

import com.riveo.functional.IFunctionND;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPenaltyFunction implements IFunctionND {

    private final Set<IFunctionND> boundaries = new HashSet<>();
    private IFunctionND target;
    private int mixMode;

    public AbstractPenaltyFunction(IFunctionND target, int mixMode) {
        this.target = target;
        this.mixMode = mixMode % 4; // Убедимся, что значение в пределах допустимого диапазона
    }

    public boolean addBoundary(IFunctionND boundary) {
        return boundaries.add(boundary);
    }

    public boolean removeBoundary(IFunctionND boundary) {
        return boundaries.remove(boundary);
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
        double boundaryResult = boundaries.stream()
                .mapToDouble(boundary -> applyPenalty(boundary, arg))
                .reduce(switch (mixMode) {
                    case 1 -> 1.0; // MUL
                    case 2 -> Double.NEGATIVE_INFINITY; // MAX
                    case 3 -> Double.POSITIVE_INFINITY; // MIN
                    default -> 0.0; // SUM
                }, (result, penaltyValue) -> switch (mixMode) {
                    case 1 -> result * penaltyValue; // MUL
                    case 2 -> Math.max(result, penaltyValue); // MAX
                    case 3 -> Math.min(result, penaltyValue); // MIN
                    default -> result + penaltyValue; // SUM
                });

        return target == null ? boundaryResult : boundaryResult + target.call(arg);
    }
}
