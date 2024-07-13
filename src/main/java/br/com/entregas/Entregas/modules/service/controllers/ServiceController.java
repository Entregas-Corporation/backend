package br.com.entregas.Entregas.modules.service.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.service.dtos.ServiceDetailDto;
import br.com.entregas.Entregas.modules.service.dtos.ServicePageDto;
import br.com.entregas.Entregas.modules.service.dtos.ServiceSaveDto;
import br.com.entregas.Entregas.modules.service.services.ServiceService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {
    private ServiceService service;

    @GetMapping("/list/valid")
    public ServicePageDto findAllServiceValid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValid(page, pageSize);
    }

    @GetMapping("/list/invalid")
    public ServicePageDto findAllServiceInvalid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalid(page, pageSize);
    }

    @GetMapping("/list/institute/valid/{id}")
    public ServicePageDto findAllServiceValidByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/institute/invalid/{id}")
    public ServicePageDto findAllServiceInvalidByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalidByInstitute(id, page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public ServiceDetailDto findOneService(@PathVariable String id) {
        return service.detail(id);
    }

    @PostMapping("/register")
    public ServiceDetailDto registerOneService(
            @Validated(GroupValidation.Create.class) ServiceSaveDto newService) {
        return service.save(newService);
    }

    @PatchMapping("/update/{id}")
    public ServiceDetailDto patchOneService(ServiceSaveDto newService, @PathVariable String id) {
        return service.update(newService, id);
    }

    @GetMapping("/toggle/activity/{id}")
    public ServiceDetailDto patchActivedByOneService(@PathVariable String id) {
        return service.toggleActivity(id);
    }

}
