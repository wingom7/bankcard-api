package com.wingom.bankcardapi.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductInputDTOTest {
    @Test
    public void testNoArgsConstructor() {
        ProductInputDTO productInputDTO = new ProductInputDTO();
        assertNotNull(productInputDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        ProductInputDTO productInputDTO = new ProductInputDTO("DEBIT");
        assertNotNull(productInputDTO);
        assertEquals("DEBIT", productInputDTO.getType());
    }

    @Test
    public void testGettersAndSetters() {
        ProductInputDTO productInputDTO = new ProductInputDTO();
        productInputDTO.setType("CREDIT");
        assertEquals("CREDIT", productInputDTO.getType());
    }

    @Test
    public void testEqualsAndHashCode() {
        ProductInputDTO productInputDTO1 = new ProductInputDTO("DEBIT");
        ProductInputDTO productInputDTO2 = new ProductInputDTO("DEBIT");
        assertEquals(productInputDTO1, productInputDTO2);
        assertEquals(productInputDTO1.hashCode(), productInputDTO2.hashCode());
    }

    @Test
    public void testToString() {
        ProductInputDTO productInputDTO = new ProductInputDTO("CREDIT");
        String productString = productInputDTO.toString();
        assertTrue(productString.contains("CREDIT"));
    }
}
