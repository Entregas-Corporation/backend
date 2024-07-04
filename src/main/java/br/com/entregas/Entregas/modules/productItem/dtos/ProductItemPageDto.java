package br.com.entregas.Entregas.modules.productItem.dtos;

import java.util.List;

public record ProductItemPageDto(
    List<ProductItemDetailDto> productItemDto, long totalElements, int totalPages
) {
    
}
