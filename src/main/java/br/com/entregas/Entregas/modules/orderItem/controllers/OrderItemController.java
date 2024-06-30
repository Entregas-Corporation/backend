package br.com.entregas.Entregas.modules.orderItem.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.modules.orderItem.models.OrderItemModel;
import br.com.entregas.Entregas.modules.orderItem.services.OrderItemService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("order-item/")
public class OrderItemController {
    private OrderItemService service;

    @PostMapping("/register")
    public List<OrderItemModel> postAllOrderItem(@RequestBody OrderItemModel newOrderItem){
        return service.save(newOrderItem);
    }
}
