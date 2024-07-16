package br.com.entregas.Entregas.modules.order.dtos;

public record ApiSaveDto(
        Integer id,
        String city,
        String name,
        Integer quantity) {
}
