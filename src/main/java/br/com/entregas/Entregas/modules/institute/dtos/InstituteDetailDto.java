package br.com.entregas.Entregas.modules.institute.dtos;

import java.time.LocalDateTime;


public record InstituteDetailDto(
        String id,
        String name,
        String description,
        String image,
        String city,
        Integer number,
        String complement,
        String longitude,
        String latitude,
        String whatsapp,
        double freight_cost_km,
        String user,
        LocalDateTime created,
        LocalDateTime updated) {
}
