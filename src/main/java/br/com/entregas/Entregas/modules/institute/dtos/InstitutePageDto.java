package br.com.entregas.Entregas.modules.institute.dtos;

import java.util.List;

public record InstitutePageDto(
    List<InstituteDetailDto> instituteDto, long totalElements, int totalPages
) {
    
}
