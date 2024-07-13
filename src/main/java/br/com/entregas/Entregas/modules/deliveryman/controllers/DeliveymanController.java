package br.com.entregas.Entregas.modules.deliveryman.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanDetailDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanPageDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanSaveDto;
import br.com.entregas.Entregas.modules.deliveryman.services.DeliverymanService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/deliveryman")
@AllArgsConstructor
public class DeliveymanController {
    private DeliverymanService service;

    @GetMapping("/list/valid")
    public DeliverymanPageDto findAllDeliverymanValid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValid(page, pageSize);
    }

    @GetMapping("/list/invalid")
    public DeliverymanPageDto findAllDeliverymanInvalid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalid(page, pageSize);
    }

    @GetMapping("/list/institute/valid/{id}")
    public DeliverymanPageDto findAllDeliverymanValidByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/institute/invalid/{id}")
    public DeliverymanPageDto findAllDeliverymanInvalidByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalidByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/user/valid/{id}")
    public DeliverymanPageDto findAllDeliverymanValidByCategory(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByUser(id, page, pageSize);
    }

    @GetMapping("/list/user/invalid/{id}")
    public DeliverymanPageDto findAllDeliverymanInvalidByCategory(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalidByUser(id, page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public DeliverymanDetailDto findOneDeliveryman(@PathVariable String id) {
        return service.detail(id);
    }

    @PostMapping("/register")
    public DeliverymanDetailDto registerOneDeliveryman(
            @Validated(GroupValidation.Create.class) DeliverymanSaveDto newDeliveryman) {
        return service.save(newDeliveryman);
    }

    @PatchMapping("/update/{id}")
    public DeliverymanDetailDto patchOneDeliveryman(DeliverymanSaveDto newDeliveryman, @PathVariable String id) {
        return service.update(newDeliveryman, id);
    }

    @GetMapping("/toggle/activity/{id}")
    public DeliverymanDetailDto patchActivedByOneDeliveryman(@PathVariable String id) {
        return service.toggleActivity(id);
    }

}
