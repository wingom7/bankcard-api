package com.wingom.bankcardapi.controllers;

import com.wingom.bankcardapi.application.dto.GenericOutputDTO;
import com.wingom.bankcardapi.application.dto.ProductDTO;
import com.wingom.bankcardapi.application.dto.ProductInputDTO;
import com.wingom.bankcardapi.application.usecases.IProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductUseCase productUseCase;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductInputDTO productInputDTO) {
        ProductDTO productDTO = productUseCase.createProduct(productInputDTO);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productsDTO = productUseCase.getAllProducts();
        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<GenericOutputDTO> deleteProduct(@PathVariable Long productId) {
        GenericOutputDTO deletedProduct = productUseCase.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }
}
