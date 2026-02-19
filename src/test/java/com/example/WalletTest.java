package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class WalletTest {

    @Test
    void testInitialState() {
        Wallet wallet = new Wallet();
        assertAll(
                () -> assertNotNull(wallet.getCards()),
                () -> assertNotNull(wallet.getCash()),
                () -> assertTrue(wallet.getCards().isEmpty()),
                () -> assertTrue(wallet.getCash().isEmpty()),
                () -> assertEquals(0.0, wallet.getCashAmount(), 0.0001)
        );
    }

    @Test
    void testSetAndGetOwner() {
        Wallet wallet = new Wallet();
        wallet.setOwner("Budi");
        assertEquals("Budi", wallet.getOwner());
    }

    @Test
    void testSetOwnerRejectsNull() {
        Wallet wallet = new Wallet();
        assertThrows(IllegalArgumentException.class, () -> wallet.setOwner(null));
    }

    @Test
    void testAddAndGetCards() {
        Wallet wallet = new Wallet();
        List<String> cards = Arrays.asList("Kartu1", "Kartu2");
        wallet.addCards(cards);
        assertIterableEquals(cards, wallet.getCards());
    }

    @Test
    void testGetCardsIsReadOnlySnapshot() {
        Wallet wallet = new Wallet();
        wallet.addCards(List.of("Kartu1"));

        List<String> snapshot = wallet.getCards();
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("Kartu2"));
        assertIterableEquals(List.of("Kartu1"), wallet.getCards());
    }

    @Test
    void testAddCardsRejectsNullListAndNullItems() {
        Wallet wallet = new Wallet();
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCards(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCards(Arrays.asList("Kartu1", null)))
        );
    }

    @Test
    void testWithdrawCards() {
        Wallet wallet = new Wallet();
        wallet.addCards(Arrays.asList("Kartu1", "Kartu2", "Kartu3"));
        wallet.withdrawCards(Arrays.asList("Kartu2"));
        assertIterableEquals(Arrays.asList("Kartu1", "Kartu3"), wallet.getCards());
    }

    @Test
    void testWithdrawCardsIgnoresMissingCard() {
        Wallet wallet = new Wallet();
        wallet.addCards(Arrays.asList("Kartu1", "Kartu2"));

        assertDoesNotThrow(() -> wallet.withdrawCards(Arrays.asList("TidakAda")));
        assertIterableEquals(Arrays.asList("Kartu1", "Kartu2"), wallet.getCards());
    }

    @Test
    void testWithdrawCardsRejectsNullListAndNullItems() {
        Wallet wallet = new Wallet();
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCards(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCards(Arrays.asList("Kartu1", null)))
        );
    }

    @Test
    void testAddAndGetCash() {
        Wallet wallet = new Wallet();
        List<Double> cash = Arrays.asList(1000.0, 2000.0);
        wallet.addCash(cash);
        assertIterableEquals(cash, wallet.getCash());
    }

    @Test
    void testGetCashIsReadOnlySnapshot() {
        Wallet wallet = new Wallet();
        wallet.addCash(List.of(1000.0));

        List<Double> snapshot = wallet.getCash();
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(2000.0));
        assertIterableEquals(List.of(1000.0), wallet.getCash());
    }

    @Test
    void testAddCashRejectsInvalidAmounts() {
        Wallet wallet = new Wallet();
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(Arrays.asList(1000.0, null))),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(Arrays.asList(0.0))),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(Arrays.asList(-500.0)))
        );
    }

    @Test
    void testWithdrawCash() {
        Wallet wallet = new Wallet();
        wallet.addCash(Arrays.asList(1000.0, 2000.0, 5000.0));
        wallet.withdrawCash(Arrays.asList(2000.0));
        assertIterableEquals(Arrays.asList(1000.0, 5000.0), wallet.getCash());
    }

    @Test
    void testWithdrawCashIgnoresMissingAmount() {
        Wallet wallet = new Wallet();
        wallet.addCash(Arrays.asList(1000.0, 2000.0));
        wallet.withdrawCash(Arrays.asList(9999.0));
        assertIterableEquals(Arrays.asList(1000.0, 2000.0), wallet.getCash());
    }

    @Test
    void testWithdrawCashRejectsNullListAndNullItems() {
        Wallet wallet = new Wallet();
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCash(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCash(Arrays.asList(1000.0, null)))
        );
    }

    @Test
    void testGetCashAmount() {
        Wallet wallet = new Wallet();
        wallet.addCash(Arrays.asList(1000.0, 2000.0, 500.0));
        assertEquals(3500.0, wallet.getCashAmount(), 0.0001);
    }
}
