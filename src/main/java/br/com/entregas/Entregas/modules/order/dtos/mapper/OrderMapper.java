package br.com.entregas.Entregas.modules.order.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.order.dtos.OrderDetailDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderSaveDto;
import br.com.entregas.Entregas.modules.order.models.OrderModel;

@Component
public class OrderMapper {
    public OrderSaveDto toDto(OrderModel order) {
        if (order == null) {
            return null;
        }
        return new OrderSaveDto(
                order.getId(),
                order.getStatus(),
                order.getPrice(),
                order.getFreight(),
                order.getDate());
    }

    public OrderDetailDto toDtoDetail(OrderModel order) {
        if (order == null) {
            return null;
        }
        return new OrderDetailDto(
                order.getId(),
                order.getStatus(),
                order.getPrice(),
                order.getFreight(),
                order.getDate(),
                order.getCreated(),
                order.getUpdated());
    }

    public OrderModel toEntity(OrderSaveDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        OrderModel order = new OrderModel();

        if (orderDto.id() != null) {
            order.setId(orderDto.id());
        }
        order.setDate(orderDto.date());
        order.setFreight(orderDto.freight());
        order.setPrice(orderDto.price());
        order.setStatus(orderDto.status());
        order.setUpdated(LocalDateTime.now());

        return order;
    }
}
