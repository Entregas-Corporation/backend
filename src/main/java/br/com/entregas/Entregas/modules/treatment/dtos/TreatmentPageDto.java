package br.com.entregas.Entregas.modules.treatment.dtos;

import java.util.List;

public record TreatmentPageDto(
    List<TreatmentDetailDto> treatmentDto, long totalElements, int totalPages
) {
    
}
