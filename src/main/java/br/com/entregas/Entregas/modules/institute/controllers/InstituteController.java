package br.com.entregas.Entregas.modules.institute.controllers;

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
import br.com.entregas.Entregas.modules.institute.dtos.InstituteDetailDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstitutePageDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteSaveDto;
import br.com.entregas.Entregas.modules.institute.services.InstituteService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/institute")
@AllArgsConstructor
public class InstituteController {
    private InstituteService service;

    @GetMapping("/list/valid")
    public InstitutePageDto findAllInstituteValid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValid(page, pageSize);
    }

    @GetMapping("/list/invalid")
    public InstitutePageDto findAllInstituteInvalid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalid(page, pageSize);
    }

    @GetMapping("/list/user/valid/{id}")
    public InstitutePageDto findAllInstituteValidByUser(@PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByUser(id, page, pageSize);
    }

    @GetMapping("/list/user/invalid/{id}")
    public InstitutePageDto findAllInstituteInvalidByUser(@PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalidByUser(id, page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public InstituteDetailDto findOneInstitute(@PathVariable String id){
        return service.detail(id);
    }

    @PostMapping("/register")
    public InstituteSaveDto registerOneInstitute(@Validated(GroupValidation.Create.class) @RequestBody InstituteSaveDto institute){
        return service.save(institute);
    }

    @PatchMapping("/update/{id}")
    public InstituteSaveDto patchOneInstitute(@RequestBody InstituteSaveDto institute, @PathVariable String id){
        return service.update(institute, id);
    }

    @PatchMapping("/toggle/activity/{id}")
    public InstituteSaveDto patchActivedByOneInstitute(@PathVariable String id){
        return service.toggleActivity(id);
    }

}
