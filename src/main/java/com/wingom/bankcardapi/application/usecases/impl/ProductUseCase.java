package com.wingom.bankcardapi.application.usecases.impl;

import com.wingom.bankcardapi.application.dto.GenericOutputDTO;
import com.wingom.bankcardapi.application.dto.ProductDTO;
import com.wingom.bankcardapi.application.dto.ProductInputDTO;
import com.wingom.bankcardapi.application.usecases.IProductUseCase;
import com.wingom.bankcardapi.domain.entities.Product;
import com.wingom.bankcardapi.domain.exceptions.EntityNotFoundException;
import com.wingom.bankcardapi.domain.exceptions.IllegalParamException;
import com.wingom.bankcardapi.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductUseCase implements IProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final SecureRandom random = new SecureRandom();

    private static final String PRODUCT_TYPE_DEBIT = "DEBIT";
    private static final String PREFIX_DEBIT_FIVE = "5";
    private static final String PREFIX_CREDIT_SIX = "6";

    private static final Map<String, String> typeNormalizationMap = Map.of(
            "CREDIT", "CREDIT",
            "CRÉDITO", "CREDIT",
            "CREDITO", "CREDIT",
            "DEBIT", "DEBIT",
            "DÉBITO", "DEBIT",
            "DEBITO", "DEBIT"
    );

    @Override
    public ProductDTO createProduct(ProductInputDTO productInputDTO) {
        String normalizedType = normalizeType(productInputDTO.getType());
        productInputDTO.setType(normalizedType);
        String productCode = generateUniqueProductCode(normalizedType);
        Product product = modelMapper.map(productInputDTO, Product.class);
        product.setProductCode(productCode);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GenericOutputDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        productRepository.delete(product );
        return new GenericOutputDTO("Product deleted successfully");
    }

    private String generateUniqueProductCode(String type) {
        String prefix = Objects.equals(type, PRODUCT_TYPE_DEBIT) ? PREFIX_DEBIT_FIVE : PREFIX_CREDIT_SIX;
        String uniquePart;
        do {
            uniquePart = String.format("%05d", random.nextInt(100000));
        } while (productRepository.existsByProductCode(prefix + uniquePart));
        return prefix + uniquePart;
    }

    private String normalizeType(String type) {
        if (type == null) {
            throw new IllegalParamException("Type cannot be null");
        }
        String normalizedType = typeNormalizationMap.get(type.toUpperCase());
        if (normalizedType == null) {
            throw new IllegalParamException("Invalid product type: " + type);
        }
        return normalizedType;
    }

}
