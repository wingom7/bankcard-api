package com.wingom.bankcardapi.controllers;

import com.wingom.bankcardapi.application.dto.GenericOutputDTO;
import com.wingom.bankcardapi.application.dto.ProductDTO;
import com.wingom.bankcardapi.application.dto.ProductInputDTO;
import com.wingom.bankcardapi.application.usecases.IProductUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductUseCase productUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createProductTest() throws Exception {
        ProductInputDTO productInputDTO = new ProductInputDTO();
        ProductDTO productDTO = new ProductDTO();

        when(productUseCase.createProduct(any(ProductInputDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productInputDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));

        verify(productUseCase, times(1)).createProduct(any(ProductInputDTO.class));
    }

    @Test
    public void getAllProductsTest() throws Exception {
        List<ProductDTO> productsDTO = List.of(new ProductDTO());

        when(productUseCase.getAllProducts()).thenReturn(productsDTO);

        mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productsDTO)));

        verify(productUseCase, times(1)).getAllProducts();
    }

    @Test
    public void deleteProductTest() throws Exception {
        Long productId = 1L;
        GenericOutputDTO genericOutputDTO = new GenericOutputDTO("Product deleted successfully");

        when(productUseCase.deleteProduct(productId)).thenReturn(genericOutputDTO);

        mockMvc.perform(delete("/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(genericOutputDTO)));

        verify(productUseCase, times(1)).deleteProduct(productId);
    }

}
