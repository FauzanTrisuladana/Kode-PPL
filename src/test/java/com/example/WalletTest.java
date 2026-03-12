package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @ParameterizedTest
    @ValueSource(doubles = {1000.0, 5000.0, 10000.0})
    void addCashMenerimaNominalPositif(double nominal) {
        Wallet wallet = new Wallet();

        wallet.addCash(nominal);

        assertEquals(nominal, wallet.getCashAmount(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1000.0, -1.0})
    void addCashMenolakNominalNegatif(double nominal) {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class, () -> wallet.addCash(nominal));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wallet-withdraw-valid.csv", numLinesToSkip = 1)
    void withdrawCashValid(double deposit, double withdraw, double expectedTotal) {
        Wallet wallet = new Wallet();
        wallet.addCash(deposit);

        wallet.withdrawCash(withdraw);

        assertEquals(expectedTotal, wallet.getCashAmount(), 0.0001);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wallet-withdraw-invalid.csv", numLinesToSkip = 1)
    void withdrawCashInvalid(double initialDeposit, double withdraw, String exceptionType) {
        Wallet wallet = new Wallet();
        if (initialDeposit > 0) {
            wallet.addCash(initialDeposit);
        }

        assertThrows(resolveExpectedException(exceptionType), () -> wallet.withdrawCash(withdraw));
    }

    @ParameterizedTest
    @MethodSource("com.example.Params#provideOwners")
    void setOwnerDenganMethodSource(Owner owner) {
        Wallet wallet = new Wallet();

        wallet.setOwner(owner);

        assertEquals(owner, wallet.getOwner());
    }

    @ParameterizedTest
    @MethodSource("com.example.Params#provideOwners")
    void constructorWalletDenganMethodSource(Owner owner) {
        Wallet wallet = new Wallet(owner);

        assertEquals(owner, wallet.getOwner());
    }

    @Test
    void setOwnerMenolakNull() {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class, () -> wallet.setOwner(null));
    }

    private Class<? extends Throwable> resolveExpectedException(String exceptionType) {
        return switch (exceptionType) {
            case "IllegalArgumentException" -> IllegalArgumentException.class;
            case "InsufficientFundsException" -> Wallet.InsufficientFundsException.class;
            default -> throw new IllegalArgumentException("exceptionType tidak dikenali: " + exceptionType);
        };
    }
}
