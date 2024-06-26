package br.com.entregas.Entregas.modules.deliveryman.dtos;

import java.time.LocalDateTime;

public record DeliverymanDetailDto(
        String id,
        String curriculum,
        String institute,
        String username,
        String email,
        LocalDateTime created,
        LocalDateTime updated) {
}
