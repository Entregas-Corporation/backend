package br.com.entregas.Entregas.modules.order.controllers;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.order.dtos.OrderDetailDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderPageDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderSaveDto;
import br.com.entregas.Entregas.modules.order.services.OrderService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private OrderService service;

    @GetMapping("/list/canceled")
    public OrderPageDto findAllOrderCanceledByUser(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listCanceledByUser(page, pageSize);
    }

    @GetMapping("/list/delivered")
    public OrderPageDto findAllOrderDeliveredByUser(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listDeliveredByUser(page, pageSize);
    }

    @GetMapping("/list/sent")
    public List<OrderDetailDto> findAllOrderSentByUser(){
        return service.listSentByUser();
    }

    @GetMapping("/list/requested")
    public List<OrderDetailDto> findAllOrderRequestedByUser(){
        return service.listRequestedByUser();
    }

    @GetMapping("/detail/{id}")
    public OrderDetailDto findOneOrder(@PathVariable String id) {
        return service.detail(id);
    }

    @PostMapping("/register")
    public OrderDetailDto registerOneOrder(
            @Validated(GroupValidation.Create.class) @RequestBody OrderSaveDto newOrder) {
        return service.save(newOrder);
    }

    @PatchMapping("/update/{id}")
    public OrderDetailDto patchOneOrder(@RequestBody OrderSaveDto newOrder, @PathVariable String id) {
        return service.update(newOrder, id);
    }

    @GetMapping("/status/canceled/{id}")
    public OrderDetailDto patchStatusByCanceled(@PathVariable String id) {
        return service.cancel(id);
    }

    @GetMapping("/status/delivered/{id}")
    public OrderDetailDto patchStatusByDelivered(@PathVariable String id) {
        return service.deliver(id);
    }

}
