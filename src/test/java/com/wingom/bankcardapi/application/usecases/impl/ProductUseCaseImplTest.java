package com.wingom.bankcardapi.application.usecases.impl;

import com.wingom.bankcardapi.application.dto.GenericOutputDTO;
import com.wingom.bankcardapi.application.dto.ProductDTO;
import com.wingom.bankcardapi.application.dto.ProductInputDTO;
import com.wingom.bankcardapi.domain.entities.Product;
import com.wingom.bankcardapi.domain.exceptions.EntityNotFoundException;
import com.wingom.bankcardapi.domain.exceptions.IllegalParamException;
import com.wingom.bankcardapi.domain.repositories.ProductRepository;

import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import org.mockito.MockitoAnnotations;

public class ProductUseCaseImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCreateProductWithValidInputThenSuccess() {
        ProductInputDTO inputDTO = new ProductInputDTO();
        inputDTO.setType("DEBIT");
        Product product = new Product();
        ProductDTO expectedDTO = new ProductDTO();

        when(modelMapper.map(inputDTO, Product.class)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(expectedDTO);

        ProductDTO result = productUseCase.createProduct(inputDTO);

        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void whenCreateProductWithNullTypeThenThrowIllegalParamException() {
        ProductInputDTO inputDTO = new ProductInputDTO();
        inputDTO.setType(null);

        assertThrows(IllegalParamException.class, () -> productUseCase.createProduct(inputDTO), "Type cannot be null");
    }

    @Test
    public void whenCreateProductWithInvalidTypeThenThrowIllegalParamException() {
        ProductInputDTO inputDTO = new ProductInputDTO();
        inputDTO.setType("INVALID");

        assertThrows(IllegalParamException.class, () -> productUseCase.createProduct(inputDTO), "Invalid product type: INVALID");
    }

    @Test
    public void whenCreateDebitProductThenGenerateCorrectPrefix() {
        ProductInputDTO debitProduct = new ProductInputDTO();
        debitProduct.setType("DEBIT");
        Product savedProduct = new Product();
        savedProduct.setProductCode("500001");

        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setProductCode("500001");

        when(modelMapper.map(any(ProductInputDTO.class), eq(Product.class))).thenReturn(new Product());
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(mockProductDTO);

        ProductDTO result = productUseCase.createProduct(debitProduct);

        assertTrue(result.getProductCode().startsWith("5"), "Product code DEBIT must start with 5");
    }

    @Test
    public void whenCreateCreditProductThenGenerateCorrectPrefix() {
        ProductInputDTO creditProduct = new ProductInputDTO();
        creditProduct.setType("CREDIT");
        Product savedProduct = new Product();
        savedProduct.setProductCode("600001");

        when(modelMapper.map(any(ProductInputDTO.class), eq(Product.class))).thenReturn(new Product());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setProductCode("600001");
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(mockProductDTO);

        ProductDTO result = productUseCase.createProduct(creditProduct);

        assertTrue(result.getProductCode().startsWith("6"), "Product code CREDIT must start with 6");
    }


    @Test
    public void whenGetAllProductsThenSuccess() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        ProductDTO productDTO1 = new ProductDTO();
        ProductDTO productDTO2 = new ProductDTO();
        when(modelMapper.map(product1, ProductDTO.class)).thenReturn(productDTO1);
        when(modelMapper.map(product2, ProductDTO.class)).thenReturn(productDTO2);

        List<ProductDTO> result = productUseCase.getAllProducts();

        assertEquals(2, result.size(), "The product list must contain exactly 2 items");
        verify(productRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(Product.class), eq(ProductDTO.class));
    }

    @Test
    public void whenDeleteProductWithValidIdThenSuccess() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        GenericOutputDTO result = productUseCase.deleteProduct(productId);

        verify(productRepository, times(1)).delete(product);
        assertEquals("Product deleted successfully", result.getMessage(), "Success message must match.");
    }

    @Test
    public void whenDeleteProductWithNonexistentIdThenThrowEntityNotFoundException() {
        Long productId = 2L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productUseCase.deleteProduct(productId), "You must throw EntityNotFoundException for a ID non-existing product.");
    }

}

