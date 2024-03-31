package com.wingom.bankcardapi.domain.entities;

import com.wingom.bankcardapi.domain.enums.CardStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    @Test
    public void testNoArgsConstructor() {
        Card card = new Card();
        assertNotNull(card);
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product();
        Card card = new Card(1L, "6890987654567876", product, "Card Holder", LocalDate.now(), BigDecimal.ZERO, CardStatusEnum.ACTIVE);
        assertNotNull(card);
        assertEquals("6890987654567876", card.getCardNumber());
    }

    @Test
    public void testGettersAndSetters() {
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber("6790987654345676");
        assertEquals(1L, card.getId());
        assertEquals("6790987654345676", card.getCardNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        Card card1 = new Card(1L, "6123456789456896", null, "Card Holder", LocalDate.now(), BigDecimal.ZERO, CardStatusEnum.ACTIVE);
        Card card2 = new Card(1L, "6123456789456896", null, "Card Holder", LocalDate.now(), BigDecimal.ZERO, CardStatusEnum.ACTIVE);
        assertEquals(card1, card2);
        assertEquals(card1.hashCode(), card2.hashCode());
    }

    @Test
    public void testToString() {
        Product product = new Product();
        Card card = new Card(1L, "5678908765432456", product, "Card Holder", LocalDate.now(), BigDecimal.ZERO, CardStatusEnum.ACTIVE);
        String cardString = card.toString();
        assertTrue(cardString.contains("5678908765432456"));
    }

}
