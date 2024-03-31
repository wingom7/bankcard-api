package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardNumberOutputDTOTest {

    @Test
    public void testNoArgsConstructor() {
        CardNumberOutputDTO cardNumberOutputDTO = new CardNumberOutputDTO();
        assertNotNull(cardNumberOutputDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        CardNumberOutputDTO cardNumberOutputDTO = new CardNumberOutputDTO("6890987654567876");
        assertNotNull(cardNumberOutputDTO);
        assertEquals("6890987654567876", cardNumberOutputDTO.getCardNumber());
    }

    @Test
    public void testGettersAndSetters() {
        CardNumberOutputDTO cardNumberOutputDTO = new CardNumberOutputDTO();
        cardNumberOutputDTO.setCardNumber("6790987654345676");
        assertEquals("6790987654345676", cardNumberOutputDTO.getCardNumber());
    }

    @Test
    public void testEqualsAndHashCode() {
        CardNumberOutputDTO cardNumberOutputDTO1 = new CardNumberOutputDTO("6890987654567876");
        CardNumberOutputDTO cardNumberOutputDTO2 = new CardNumberOutputDTO("6890987654567876");
        assertEquals(cardNumberOutputDTO1, cardNumberOutputDTO2);
        assertEquals(cardNumberOutputDTO2.hashCode(), cardNumberOutputDTO2.hashCode());
    }

    @Test
    public void testToString() {
        CardNumberOutputDTO cardNumberOutputDTO = new CardNumberOutputDTO("5678908765432456");
        String cardString = cardNumberOutputDTO.toString();
        assertTrue(cardString.contains("5678908765432456"));
    }
}
