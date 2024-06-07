package br.com.entregas.Entregas.modules.productCategory.dtos;

import java.util.List;

public record ProductCategoryPageDto(
    List<ProductCategoryDetailDto> productCategoryDto, long totalElements, int totalPages
) {
    
}
