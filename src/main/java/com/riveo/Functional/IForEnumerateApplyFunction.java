package com.riveo.Functional;

@FunctionalInterface
public interface IForEnumerateApplyFunction<T>{
    T call(int elementIndex, T element);
}