package com.wingom.bankcardapi.application.usecases;

import com.wingom.bankcardapi.application.dto.GenericOutputDTO;
import com.wingom.bankcardapi.application.dto.ProductDTO;
import com.wingom.bankcardapi.application.dto.ProductInputDTO;

import java.util.List;

public interface IProductUseCase {
    ProductDTO createProduct(ProductInputDTO productInputDTO);
    List<ProductDTO> getAllProducts();
    GenericOutputDTO deleteProduct(Long productId);
}
