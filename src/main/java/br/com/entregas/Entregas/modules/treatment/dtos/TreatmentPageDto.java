package br.com.entregas.Entregas.modules.treatment.dtos;

import java.util.List;

public record TreatmentPageDto(
    List<TreatmentDetailDto> instituteDto, long totalElements, int totalPages
) {
    
}
