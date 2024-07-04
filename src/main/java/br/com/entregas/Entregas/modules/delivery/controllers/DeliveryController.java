package br.com.entregas.Entregas.modules.delivery.controllers;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.delivery.dtos.DeliveryDetailDto;
import br.com.entregas.Entregas.modules.delivery.dtos.DeliveryPageDto;
import br.com.entregas.Entregas.modules.delivery.dtos.DeliverySaveDto;
import br.com.entregas.Entregas.modules.delivery.services.DeliveryService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {
    private DeliveryService service;

    @PostMapping("/register")
    public DeliveryDetailDto registerOneDelivery(
            @Validated(GroupValidation.Create.class) @RequestBody DeliverySaveDto institute) {
        return service.save(institute);
    }

    @GetMapping("/list/deliveryman/sent/{id}")
    public List<DeliveryDetailDto> findAllDeliveryByOrderSentByDeliverymanId(@PathVariable String id) {
        return service.ListOrdersSentByDeliverymanId(id);
    }

    @GetMapping("/list/deliveryman/canceled/{id}")
    public DeliveryPageDto findAllDeliveryByOrderCanceledByDeliverymanId(@PathVariable String id, @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.ListOrdersCanceledByDeliverymanId(id, page, pageSize);
    }

    @GetMapping("/list/deliveryman/delivered/{id}")
    public DeliveryPageDto findAllDeliveryByOrderDeliveredByDeliverymanId(@PathVariable String id, @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.ListOrdersDeliveredtByDeliverymanId(id, page, pageSize);
    }

    @GetMapping("/list/order/{id}")
    public List<DeliveryDetailDto> findAllDeliveryByDeliverymenByOrder(@PathVariable String id) {
        return service.ListDeliverymenByOrder(id);
    }
}
