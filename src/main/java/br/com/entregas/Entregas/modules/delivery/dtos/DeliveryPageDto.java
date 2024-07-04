package br.com.entregas.Entregas.modules.delivery.dtos;

import java.util.List;

public record DeliveryPageDto(
    List<DeliveryDetailDto> deliveryDto, long totalElements, int totalPages
) {
    
}
