package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

class KalkulatorTest {

    private static Stream<Arguments> provideParameters(){
        return Stream.of(
            Arguments.of(2, 4, 6),
            Arguments.of(1, 2, 3),
            Arguments.of(100, 500, 600)
        );
    }

    @Test
    void testTambah() {
        Kalkulator kalkulator = new Kalkulator(5, 3);
        assertEquals(8, kalkulator.tambah());
    }

    @Test
    void testKurang() {
        Kalkulator kalkulator = new Kalkulator(5, 3);
        assertEquals(2, kalkulator.kurang());
    }

    @Test
    void testKali() {
        Kalkulator kalkulator = new Kalkulator(5, 3);
        assertEquals(15, kalkulator.kali());
    }

    @Test
    void testBagi() {
        Kalkulator kalkulator = new Kalkulator(6, 3);
        assertEquals(2, kalkulator.bagi());
    }

    @ParameterizedTest(name = "Eksekusi ke-{index} nilai : {arguments}")
    @ValueSource(ints = {2, 4, 6})
    void testCekEven(int param) {
        Kalkulator kalkulator = new Kalkulator();
        assertTrue(kalkulator.cekEven(param));
    }

    @ParameterizedTest
    @CsvSource({"2, 4, 6", "1, 2, 3", "100, 500, 600"})
    void testTambahCsv(int a, int b, int expected) {
        Kalkulator kalkulator = new Kalkulator(a, b);
        int result = kalkulator.tambah();
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void testTambahParams(int a, int b, int expected) {
        Kalkulator kalkulator = new Kalkulator(a, b);
        int result = kalkulator.tambah();
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("com.example.Params#provideParameters")
    void testTambahParamsFromExternal(int a, int b, int expected) {
        Kalkulator kalkulator = new Kalkulator(a, b);
        int result = kalkulator.tambah();
        assertEquals(expected, result);
    }
}
