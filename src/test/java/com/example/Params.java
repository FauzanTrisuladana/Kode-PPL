package com.example;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class Params {
    public static Stream<Arguments> provideParameters(){
        return Stream.of(
            Arguments.of(2, 4, 6),
            Arguments.of(1, 2, 3),
            Arguments.of(100, 500, 600)
        );
    }
}
