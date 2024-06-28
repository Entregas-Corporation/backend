package br.com.entregas.Entregas.modules.productItem.dtos;

import java.time.LocalDateTime;

public record ProductItemDetailDto(
        String id,
        String product,
        Integer quantity,
        Double price,
        LocalDateTime created,
        LocalDateTime updated) {
}
