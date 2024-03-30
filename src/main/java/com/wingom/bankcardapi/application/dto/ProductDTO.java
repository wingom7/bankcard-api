package com.wingom.bankcardapi.application.dto;

import com.wingom.bankcardapi.domain.enums.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String productCode;
    private ProductTypeEnum type;
}
