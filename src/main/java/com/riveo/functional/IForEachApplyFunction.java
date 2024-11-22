package com.riveo.functional;

@FunctionalInterface
public interface IForEachApplyFunction<T>{
    T call(T element);
}