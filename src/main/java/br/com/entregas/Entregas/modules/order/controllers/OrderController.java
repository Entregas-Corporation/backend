package br.com.entregas.Entregas.modules.order.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.modules.order.dtos.ApiSaveDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderDetailDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderPageDto;
import br.com.entregas.Entregas.modules.order.services.OrderService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private OrderService service;

    @GetMapping("/api/city-sells/list")
    public List<ApiSaveDto> findAllOrderDeliveredByCitySellsToApi(){
        return service.listOrderDeliveredByCitySells();
    }

    @GetMapping("/api/city-buy/list")
    public List<ApiSaveDto> findAllOrderDeliveredByCityBuyToApi(){
        return service.listOrderDeliveredByCityBuy();
    }

    @GetMapping("/list/user/canceled/{id}")
    public OrderPageDto findAllOrderCanceledByUser(
        @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listCanceledByUser(id, page, pageSize);
    }

    @GetMapping("/list/user/delivered/{id}")
    public OrderPageDto findAllOrderDeliveredByUser(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listDeliveredByUser(id, page, pageSize);
    }

    @GetMapping("/list/user/sent/{id}")
    public List<OrderDetailDto> findAllOrderSentByUser(@PathVariable String id) {
        return service.listSentByUser(id);
    }

    @GetMapping("/list/user/requested/{id}")
    public List<OrderDetailDto> findAllOrderRequestedByUser(@PathVariable String id) {
        return service.listRequestedByUser(id);
    }



    @GetMapping("/list/institute/canceled/{id}")
    public OrderPageDto findAllOrderCanceledByInstitute(
        @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listCanceledByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/institute/delivered/{id}")
    public OrderPageDto findAllOrderDeliveredByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listDeliveredByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/institute/sent/{id}")
    public List<OrderDetailDto> findAllOrderSentByInstitute(@PathVariable String id) {
        return service.listSentByInstitute(id);
    }

    @GetMapping("/list/institute/requested/{id}")
    public List<OrderDetailDto> findAllOrderRequestedByInstitute(@PathVariable String id) {
        return service.listRequestedByInstitute(id);
    }


    @GetMapping("/detail/{id}")
    public OrderDetailDto findOneOrder(@PathVariable String id) {
        return service.detail(id);
    }


    @GetMapping("/status/canceled/{id}")
    public OrderDetailDto patchStatusByCanceled(@PathVariable String id) {
        return service.cancel(id);
    }

    @GetMapping("/status/delivered/{id}")
    public OrderDetailDto patchStatusByDelivered(@PathVariable String id) {
        return service.deliver(id);
    }


    @GetMapping("/status/sent/{id}")
    public OrderDetailDto patchStatusBySent(@PathVariable String id) {
        return service.send(id);
    }

}