package com.riveo.Functional;

@FunctionalInterface
public interface IReduceFunction <T>{
    T call(T accumulator, T value);
}