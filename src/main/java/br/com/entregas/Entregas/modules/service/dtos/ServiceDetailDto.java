package br.com.entregas.Entregas.modules.service.dtos;

import java.time.LocalDateTime;

import br.com.entregas.Entregas.modules.service.enums.ServiceMode;

public record ServiceDetailDto(
        String id,
        String name,
        String description,
        ServiceMode mode,
        Double price,
        String institute,
        LocalDateTime created,
        LocalDateTime updated) {
}
