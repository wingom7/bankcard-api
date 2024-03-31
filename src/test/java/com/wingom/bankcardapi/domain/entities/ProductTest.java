package com.wingom.bankcardapi.domain.entities;

import com.wingom.bankcardapi.domain.enums.ProductTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    public void testNoArgsConstructor() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    public void testAllArgsConstructor() {
        Product product = new Product(1L, "865432", ProductTypeEnum.CREDIT);
        assertNotNull(product);
        assertEquals("865432", product.getProductCode());
    }

    @Test
    public void testGettersAndSetters() {
        Product product = new Product();
        product.setId(1L);
        product.setProductCode("654356");
        assertEquals(1L, product.getId());
        assertEquals("654356", product.getProductCode());
    }

    @Test
    public void testEqualsAndHashCode() {
        Product product1 = new Product(1L, "465432", ProductTypeEnum.DEBIT);
        Product product2 = new Product(1L, "465432", ProductTypeEnum.DEBIT);
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testToString() {
        Product product = new Product(1L, "965434", ProductTypeEnum.DEBIT);
        String productString = product.toString();
        assertTrue(productString.contains("965434"));
    }
}
