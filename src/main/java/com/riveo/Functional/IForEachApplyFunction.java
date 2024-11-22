package com.riveo.Functional;

@FunctionalInterface
public interface IForEachApplyFunction<T>{
    T call(T element);
}