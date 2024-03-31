package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericOutputDTOTest {

    @Test
    public void testNoArgsConstructor() {
        GenericOutputDTO genericOutputDTO = new GenericOutputDTO();
        assertNotNull(genericOutputDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        GenericOutputDTO genericOutputDTO = new GenericOutputDTO("test message");
        assertNotNull(genericOutputDTO);
        assertEquals("test message", genericOutputDTO.getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        GenericOutputDTO genericOutputDTO = new GenericOutputDTO();
        genericOutputDTO.setMessage("test message");
        assertEquals("test message", genericOutputDTO.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        GenericOutputDTO genericOutputDTO1 = new GenericOutputDTO("test message");
        GenericOutputDTO genericOutputDTO2 = new GenericOutputDTO("test message");
        assertEquals(genericOutputDTO1, genericOutputDTO2);
        assertEquals(genericOutputDTO2.hashCode(), genericOutputDTO2.hashCode());
    }

    @Test
    public void testToString() {
        GenericOutputDTO genericOutputDTO = new GenericOutputDTO("test message");
        String cardString = genericOutputDTO.toString();
        assertTrue(cardString.contains("test message"));
    }
}
