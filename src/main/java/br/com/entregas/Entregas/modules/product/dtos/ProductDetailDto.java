package br.com.entregas.Entregas.modules.product.dtos;

import java.time.LocalDateTime;

public record ProductDetailDto(
        String id,
        String name,
        String description,
        String image,
        Double price,
        Integer quantity,
        String institute,
        String category,
        LocalDateTime created,
        LocalDateTime updated) {
}
