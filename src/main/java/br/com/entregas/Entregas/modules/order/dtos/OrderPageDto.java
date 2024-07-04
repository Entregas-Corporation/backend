package br.com.entregas.Entregas.modules.order.dtos;

import java.util.List;

public record OrderPageDto(
    List<OrderDetailDto> orderDto, long totalElements, int totalPages
) {
    
}
