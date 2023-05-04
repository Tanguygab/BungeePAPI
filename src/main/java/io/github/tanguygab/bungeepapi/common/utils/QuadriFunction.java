package io.github.tanguygab.bungeepapi.common.utils;

@FunctionalInterface
public interface QuadriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}
