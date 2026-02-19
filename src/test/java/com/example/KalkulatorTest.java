package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KalkulatorTest {

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
}
