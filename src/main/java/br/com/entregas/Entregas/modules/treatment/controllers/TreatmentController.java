package br.com.entregas.Entregas.modules.treatment.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentDetailDto;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentPageDto;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentSaveDto;
import br.com.entregas.Entregas.modules.treatment.services.TreatmentService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/treatment")
@AllArgsConstructor
public class TreatmentController {
    private TreatmentService service;

    @GetMapping("/list/support/resolved")
    public TreatmentPageDto findAllTreatmentResolvedSupport(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listResolvedSupport(page, pageSize);
    }

    @GetMapping("/list/support/not-resolved")
    public TreatmentPageDto findAllTreatmentNotResolvedSupport(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listNotResolvedSupport(page, pageSize);
    }

    @GetMapping("/list/complaint/resolved")
    public TreatmentPageDto findAllTreatmentResolvedComplaint(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listResolvedComplaint(page, pageSize);
    }

    
    @GetMapping("/list/complaint/not-resolved")
    public TreatmentPageDto findAllTreatmentNotResolvedComplaint(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listNotResolvedComplaint(page, pageSize);
    }

    @GetMapping("/list/institute/resolved/{id}")
    public TreatmentPageDto findAllTreatmentResolvedByInstitute(@PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listResolvedComplaintByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/institute/not-resolved/{id}")
    public TreatmentPageDto findAllTreatmentNotResolvedByInstitute(@PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listNotResolvedComplaintByInstitute(id, page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public TreatmentDetailDto findOneTreatment(@PathVariable String id){
        return service.detail(id);
    }

    @PostMapping("/register")
    public TreatmentDetailDto registerOneTreatment(@Validated(GroupValidation.Create.class) @RequestBody TreatmentSaveDto Treatment){
        return service.save(Treatment);
    }

    @GetMapping("/toggle/status-resolved/{id}")
    public TreatmentDetailDto patchActivedByOneTreatment(@PathVariable String id){
        return service.toggleStatusResolved(id);
    }

}
