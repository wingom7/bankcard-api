package com.wingom.bankcardapi.application.dto;

import com.wingom.bankcardapi.domain.enums.ProductTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductDTOTest {
    @Test
    public void testNoArgsConstructor() {
        ProductDTO productDTO = new ProductDTO();
        assertNotNull(productDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        ProductDTO productDTO = new ProductDTO(1L, "865432", ProductTypeEnum.CREDIT);
        assertNotNull(productDTO);
        assertEquals("865432", productDTO.getProductCode());
    }

    @Test
    public void testGettersAndSetters() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setProductCode("654356");
        assertEquals(1L, productDTO.getId());
        assertEquals("654356", productDTO.getProductCode());
    }

    @Test
    public void testEqualsAndHashCode() {
        ProductDTO productDTO1 = new ProductDTO(1L, "465432", ProductTypeEnum.DEBIT);
        ProductDTO productDTO2 = new ProductDTO(1L, "465432", ProductTypeEnum.DEBIT);
        assertEquals(productDTO1, productDTO2);
        assertEquals(productDTO1.hashCode(), productDTO2.hashCode());
    }

    @Test
    public void testToString() {
        ProductDTO productDTO = new ProductDTO(1L, "965434", ProductTypeEnum.DEBIT);
        String productString = productDTO.toString();
        assertTrue(productString.contains("965434"));
    }
}
