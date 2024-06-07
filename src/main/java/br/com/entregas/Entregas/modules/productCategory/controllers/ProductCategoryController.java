package br.com.entregas.Entregas.modules.productCategory.controllers;

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
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategoryDetailDto;
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategoryPageDto;
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategorySaveDto;
import br.com.entregas.Entregas.modules.productCategory.services.ProductCategoryService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-category")
@AllArgsConstructor
public class ProductCategoryController {
    private ProductCategoryService service;

    @GetMapping("/list/valid")
    public ProductCategoryPageDto findAllProductCategoryValid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValid(page, pageSize);
    }

    @GetMapping("/list/invalid")
    public ProductCategoryPageDto findAllProductCategoryInvalid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalid(page, pageSize);
    }


    @GetMapping("/detail/{id}")
    public ProductCategoryDetailDto findOneProductCategory(@PathVariable String id){
        return service.detail(id);
    }

    @PostMapping("/register")
    public ProductCategorySaveDto registerOneProductCategory(@Validated(GroupValidation.Create.class) @RequestBody ProductCategorySaveDto productCategory){
        return service.save(productCategory);
    }

    @PatchMapping("/update/{id}")
    public ProductCategorySaveDto patchOneProductCategory(@RequestBody ProductCategorySaveDto productCategory, @PathVariable String id){
        return service.update(productCategory, id);
    }

    @PatchMapping("/toggle/activity/{id}")
    public ProductCategorySaveDto patchActivedByOneProductCategory(@PathVariable String id){
        return service.toggleActivity(id);
    }

}
