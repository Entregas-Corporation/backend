package br.com.entregas.Entregas.modules.service.dtos;

import java.util.List;

public record ServicePageDto(
    List<ServiceDetailDto> serviceDto, long totalElements, int totalPages
) {
    
}
