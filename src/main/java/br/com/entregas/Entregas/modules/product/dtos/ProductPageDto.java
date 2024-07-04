package br.com.entregas.Entregas.modules.product.dtos;

import java.util.List;

public record ProductPageDto(
    List<ProductDetailDto> productDto, long totalElements, int totalPages
) {
    
}
