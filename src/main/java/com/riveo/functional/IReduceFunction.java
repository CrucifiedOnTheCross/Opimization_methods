package com.riveo.functional;

@FunctionalInterface
public interface IReduceFunction <T>{
    T call(T accumulator, T value);
}