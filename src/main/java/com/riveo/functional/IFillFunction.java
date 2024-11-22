package com.riveo.functional;

@FunctionalInterface
public interface IFillFunction<T>{
    T call(int index);
}