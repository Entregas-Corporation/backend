package br.com.entregas.Entregas.modules.order.dtos;

import java.time.LocalDateTime;
import java.util.Date;

import br.com.entregas.Entregas.modules.order.enums.StatusOrder;

public record OrderDetailDto(
        String id,
        StatusOrder status,
        Double price,
        Double freight,
        Date date,
        LocalDateTime created,
        LocalDateTime updated) {
}
