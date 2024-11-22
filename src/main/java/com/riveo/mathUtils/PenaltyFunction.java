package com.riveo.mathUtils;

import com.riveo.functional.IFunctionND;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class PenaltyFunction implements IFunctionND, Iterable<IFunctionND> {
    private int _penaltyFunctionMixMode;

    public boolean innerPenalty = false;

    private static double wrapInternal(IFunctionND function, DoubleVector arg) {
        return Math.pow(1.0 / function.call(arg), 12);
    }

    private static double wrapExternal(IFunctionND function, DoubleVector arg) {
        return Math.pow(Math.max(0.0, function.call(arg)), 4);
    }

    private final Set<IFunctionND> _boundaries;

    private IFunctionND _target;

    public int penaltyFunctionMixMode() {
        return _penaltyFunctionMixMode;
    }

    public void penaltyFunctionMixMode(int value) {
        _penaltyFunctionMixMode = value % 4;
    }

    public IFunctionND target() {
        return _target;
    }

    public IFunctionND target(IFunctionND target) {
        return _target = target;
    }

    public boolean appendBound(IFunctionND bound) {
        if (_boundaries.contains(bound))
            return false;
        _boundaries.add(bound);
        return true;
    }

    public boolean removeBound(IFunctionND bound) {
        if (!_boundaries.contains(bound))
            return false;
        _boundaries.remove(bound);
        return true;
    }

    public boolean appendBoundaries(IFunctionND... boundaries) {
        boolean result = false;
        for (IFunctionND bound : boundaries)
            result |= appendBound(bound);
        return result;
    }

    public boolean removeBoundaries(IFunctionND... boundaries) {
        boolean result = false;
        for (IFunctionND bound : boundaries)
            result |= removeBound(bound);
        return result;
    }

    public PenaltyFunction() {
        _boundaries = new HashSet<>();
        _target = v -> (v.dot(v));
    }

    @Override
    public double call(final DoubleVector arg) {
        double result = 0.0f;
        switch (penaltyFunctionMixMode()) {
            case 1 /*MUL*/:
                for (IFunctionND bound : this) result *= bound.call(arg);
                break;
            case 2 /*MAX*/:
                for (IFunctionND bound : this) result = Math.max(result, bound.call(arg));
                break;
            case 3 /*MIN*/:
                for (IFunctionND bound : this) result = Math.min(result, bound.call(arg));
                break;
            default: /*SUM*/
                for (IFunctionND bound : this) result += bound.call(arg);
                break;
        }
        result += target() == null ? 0.0 : target().call(arg);
        return result;
    }

    @Override
    public Iterator<IFunctionND> iterator() {
        return new Iterator<>() {
            final Iterator<IFunctionND> _boundariesIterator = _boundaries.iterator();

            @Override
            public boolean hasNext() {
                return _boundariesIterator.hasNext();
            }

            @Override
            public IFunctionND next() {
                return innerPenalty ? v -> wrapInternal(_boundariesIterator.next(), v) : v -> wrapExternal(_boundariesIterator.next(), v);
            }
        };
    }
}