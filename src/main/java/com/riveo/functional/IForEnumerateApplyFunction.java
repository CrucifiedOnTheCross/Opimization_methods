package com.riveo.functional;

@FunctionalInterface
public interface IForEnumerateApplyFunction<T>{
    T call(int elementIndex, T element);
}