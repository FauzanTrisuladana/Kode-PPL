package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletTest {

    Wallet wallet;

    @BeforeAll
    void BeforeAll() {
        wallet = new Wallet("Budi");
    }

    @AfterAll
    void AfterAll() {
        wallet = null;
    }

    @BeforeEach
    void BeforeEach() {
        wallet = new Wallet("Budi");
    }

    @AfterEach
    void AfterEach() {
        wallet = null;
    }

    @Test
    @Order(1)
    void testInitialState() {
        assertAll(
                () -> assertNotNull(wallet.getCards()),
                () -> assertNotNull(wallet.getCash()),
                () -> assertTrue(wallet.getCards().isEmpty()),
                () -> assertTrue(wallet.getCash().isEmpty()),
                () -> assertEquals(0.0, wallet.getCashAmount(), 0.0001)
        );
    }

    @Test
    void testSetOwnerRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> wallet.setOwner(null));
    }

    @Test
    @Order(2)
    void testAddAndGetCards() {
        List<String> cards = Arrays.asList("Kartu1", "Kartu2");
        wallet.addCards(cards);
        assertIterableEquals(cards, wallet.getCards());
    }

    @Test
    void testGetCardsIsReadOnlySnapshot() {
        wallet.addCards(List.of("Kartu1"));

        List<String> snapshot = wallet.getCards();
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add("Kartu2"));
        assertIterableEquals(List.of("Kartu1"), wallet.getCards());
    }

    @Test
    void testAddCardsRejectsNullListAndNullItems() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCards(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCards(Arrays.asList("Kartu1", null)))
        );
    }

    @Test
    @Order(3)
    void testWithdrawCards() {
        wallet.addCards(Arrays.asList("Kartu1", "Kartu2", "Kartu3"));
        wallet.withdrawCards(Arrays.asList("Kartu2"));
        assertIterableEquals(Arrays.asList("Kartu1", "Kartu3"), wallet.getCards());
    }

    @Test
    void testWithdrawCardsIgnoresMissingCard() {
        wallet.addCards(Arrays.asList("Kartu1", "Kartu2"));

        assertDoesNotThrow(() -> wallet.withdrawCards(Arrays.asList("TidakAda")));
        assertIterableEquals(Arrays.asList("Kartu1", "Kartu2"), wallet.getCards());
    }

    @Test
    void testWithdrawCardsRejectsNullListAndNullItems() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCards(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCards(Arrays.asList("Kartu1", null)))
        );
    }

    @Test
    @Order(4)
    void testAddAndGetCash() {
        List<Double> cash = Arrays.asList(1000.0, 2000.0);
        wallet.addCash(cash);
        assertIterableEquals(cash, wallet.getCash());
    }

    @Test
    void testGetCashIsReadOnlySnapshot() {
        wallet.addCash(List.of(1000.0));

        List<Double> snapshot = wallet.getCash();
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(2000.0));
        assertIterableEquals(List.of(1000.0), wallet.getCash());
    }

    @Test
    void testAddCashRejectsInvalidAmounts() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(Arrays.asList(1000.0, null))),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(Arrays.asList(0.0))),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.addCash(Arrays.asList(-500.0)))
        );
    }

    @Test
    @Order(5)
    void testWithdrawCash() {
        wallet.addCash(Arrays.asList(1000.0, 2000.0, 5000.0));
        wallet.withdrawCash(Arrays.asList(2000.0));
        assertIterableEquals(Arrays.asList(1000.0, 5000.0), wallet.getCash());
    }

    @Test
    void testWithdrawCashIgnoresMissingAmount() {
        wallet.addCash(Arrays.asList(1000.0, 2000.0));
        wallet.withdrawCash(Arrays.asList(9999.0));
        assertIterableEquals(Arrays.asList(1000.0, 2000.0), wallet.getCash());
    }

    @Test
    void testWithdrawCashRejectsNullListAndNullItems() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCash(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> wallet.withdrawCash(Arrays.asList(1000.0, null)))
        );
    }

    @Test
    void testGetCashAmount() {
        wallet.addCash(Arrays.asList(1000.0, 2000.0, 500.0));
        assertEquals(3500.0, wallet.getCashAmount(), 0.0001);
    }
}
