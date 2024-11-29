package com.riveo.mathutils;

import com.riveo.functional.IFunctionND;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractPenaltyFunction implements IFunctionND {

    private static final Logger LOGGER = Logger.getLogger(AbstractPenaltyFunction.class.getName());

    private final Set<IFunctionND> boundaries = new HashSet<>();
    private IFunctionND target;
    private MixMode mixMode;

    public AbstractPenaltyFunction(IFunctionND target, MixMode mixMode) {
        this.target = target;
        this.mixMode = mixMode != null ? mixMode : MixMode.SUM; // Значение по умолчанию SUM
    }

    public AbstractPenaltyFunction(IFunctionND target, int mixMode) {
        this.target = target;
        try {
            this.mixMode = MixMode.fromValue(mixMode);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid mix mode: {0}. Defaulting to SUM mode.", mixMode);
            this.mixMode = MixMode.SUM;
        }
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

    public enum MixMode {
        SUM(0),
        MUL(1),
        MAX(2),
        MIN(3);

        private final int value;

        MixMode(int value) {
            this.value = value;
        }

        public static MixMode fromValue(int value) {
            for (MixMode mode : MixMode.values()) {
                if (mode.value == value) {
                    return mode;
                }
            }
            throw new IllegalArgumentException("Invalid MixMode value: " + value);
        }
    }
}
