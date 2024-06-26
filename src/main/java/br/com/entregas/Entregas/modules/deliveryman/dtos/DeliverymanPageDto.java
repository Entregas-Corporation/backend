package br.com.entregas.Entregas.modules.deliveryman.dtos;

import java.util.List;

public record DeliverymanPageDto(
    List<DeliverymanDetailDto> productCategoryDto, long totalElements, int totalPages
) {
    
}
