package com.riveo.Functional;

@FunctionalInterface
public interface IConditionFunction<T> {
    boolean call(T element);
}