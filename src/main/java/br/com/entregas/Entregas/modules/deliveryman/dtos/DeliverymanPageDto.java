package br.com.entregas.Entregas.modules.deliveryman.dtos;

import java.util.List;

public record DeliverymanPageDto(
    List<DeliverymanDetailDto> deliverymanDto, long totalElements, int totalPages
) {
    
}
