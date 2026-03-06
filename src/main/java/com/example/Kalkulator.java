package com.example;

public class Kalkulator {
    int a, b;

    public Kalkulator(int a, int b) {
        this.a = a;
        this.b = b;
    }
    public Kalkulator() {
        
    }

    public int tambah() {
        return a + b;
    }

    public int kurang() {
        return a - b;
    }

    public int kali() {
        return a * b;
    }

    public int bagi() {
        return a / b;
    }

    public boolean cekEven(int number) {
        return number % 2 == 0;
    }
}