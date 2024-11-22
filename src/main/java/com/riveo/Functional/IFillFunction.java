package com.riveo.Functional;

@FunctionalInterface
public interface IFillFunction<T>{
    T call(int index);
}