package br.com.entregas.Entregas.modules.productItem.dtos;

import java.util.List;

public record ProductItemPageDto(
    List<ProductItemDetailDto> productCategoryDto, long totalElements, int totalPages
) {
    
}
