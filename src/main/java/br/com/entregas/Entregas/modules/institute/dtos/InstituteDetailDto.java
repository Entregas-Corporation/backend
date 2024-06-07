package br.com.entregas.Entregas.modules.institute.dtos;

import java.time.LocalDateTime;



public record InstituteDetailDto(
        String id,
        String name,
        String description,
        String image,
        String city,
        String longitude,
        String latitude,
        String whatsapp,
        double freight_cost_km,
        LocalDateTime created,
        LocalDateTime updated) {
}
