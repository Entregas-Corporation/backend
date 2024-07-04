package br.com.entregas.Entregas.modules.delivery.dtos;

import java.util.List;

public record DeliveryPageDto(
    List<DeliveryDetailDto> instituteDto, long totalElements, int totalPages
) {
    
}
