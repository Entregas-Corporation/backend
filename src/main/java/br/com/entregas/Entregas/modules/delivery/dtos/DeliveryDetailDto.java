package br.com.entregas.Entregas.modules.delivery.dtos;

import java.time.LocalDateTime;

public record DeliveryDetailDto(
        String id,
        String deliveryman,
        String order,
        LocalDateTime created,
        LocalDateTime updated) {
}
