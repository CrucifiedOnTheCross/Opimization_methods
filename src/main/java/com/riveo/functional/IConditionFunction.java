package com.riveo.functional;

@FunctionalInterface
public interface IConditionFunction<T> {
    boolean call(T element);
}