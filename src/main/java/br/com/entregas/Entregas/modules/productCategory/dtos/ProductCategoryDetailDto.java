package br.com.entregas.Entregas.modules.productCategory.dtos;

import java.time.LocalDateTime;



public record ProductCategoryDetailDto(
        String id,
        String name,
        LocalDateTime created,
        LocalDateTime updated) {
}
