package br.com.entregas.Entregas.modules.order.dtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.orderItem.models.OrderItemModel;

public record OrderDetailDto(
        String id,
        StatusOrder status,
        Double price,
        Double freight,
        Double total,
        Date date,
        String institute,
        String userName,
        String userEmail,
        List<OrderItemModel> orders,
        LocalDateTime created,
        LocalDateTime updated) {
}
