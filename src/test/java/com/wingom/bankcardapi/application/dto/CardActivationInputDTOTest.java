package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardActivationInputDTOTest {

    @Test
    public void testNoArgsConstructor() {
        CardActivationInputDTO cardActivationInputDTO = new CardActivationInputDTO();
        assertNotNull(cardActivationInputDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        CardActivationInputDTO cardActivationInputDTO = new CardActivationInputDTO("6890987654567876");
        assertNotNull(cardActivationInputDTO);
        assertEquals("6890987654567876", cardActivationInputDTO.getCardId());
    }

    @Test
    public void testGettersAndSetters() {
        CardActivationInputDTO cardActivationInputDTO = new CardActivationInputDTO();
        cardActivationInputDTO.setCardId("6790987654345676");
        assertEquals("6790987654345676", cardActivationInputDTO.getCardId());
    }

    @Test
    public void testEqualsAndHashCode() {
        CardActivationInputDTO cardActivationInputDTO1 = new CardActivationInputDTO("6890987654567876");
        CardActivationInputDTO cardActivationInputDTO2 = new CardActivationInputDTO("6890987654567876");
        assertEquals(cardActivationInputDTO1, cardActivationInputDTO2);
        assertEquals(cardActivationInputDTO2.hashCode(), cardActivationInputDTO2.hashCode());
    }

    @Test
    public void testToString() {
        CardActivationInputDTO cardActivationInputDTO = new CardActivationInputDTO("5678908765432456");
        String cardString = cardActivationInputDTO.toString();
        assertTrue(cardString.contains("5678908765432456"));
    }
}
