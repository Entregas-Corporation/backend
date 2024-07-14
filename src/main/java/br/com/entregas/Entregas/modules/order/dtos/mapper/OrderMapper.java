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
                order.getFreight(),
                order.getInstitute(),
                order.getUserName(),
                order.getUserEmail(),
                order.getDate()
                );
    }

    public OrderDetailDto toDtoDetail(OrderModel order) {
        if (order == null) {
            return null;
        }
        return new OrderDetailDto(
                order.getId(),
                order.getStatus(),
                order.getFreight(),
                order.getDate(),
                order.getInstitute().getName(),
                order.getUserName(),
                order.getUserEmail(),
                order.getOrders(),
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
        order.setStatus(orderDto.status());
        order.setInstitute(orderDto.institute());
        order.setUserName(orderDto.userName());
        order.setUserEmail(order.getUserEmail());
        order.setUpdated(LocalDateTime.now());

        return order;
    }
}
