package br.com.entregas.Entregas.modules.product.controllers;

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
import br.com.entregas.Entregas.modules.product.services.ProductService;
import br.com.entregas.Entregas.modules.product.dtos.ProductDetailDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductPageDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductSaveDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private ProductService service;

    @GetMapping("/list/valid")
    public ProductPageDto findAllProductValid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValid(page, pageSize);
    }

    @GetMapping("/list/invalid")
    public ProductPageDto findAllProductInvalid(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalid(page, pageSize);
    }

    @GetMapping("/list/institute/valid/{id}")
    public ProductPageDto findAllProductValidByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/institute/invalid/{id}")
    public ProductPageDto findAllProductInvalidByInstitute(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalidByInstitute(id, page, pageSize);
    }

    @GetMapping("/list/product-category/valid/{id}")
    public ProductPageDto findAllProductValidByCategory(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByCategory(id, page, pageSize);
    }

    @GetMapping("/list/product-category/invalid/{id}")
    public ProductPageDto findAllProductInvalidByCategory(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listInvalidByCategory(id, page, pageSize);
    }

    @GetMapping("/list/institute/product-category/valid/{id_institute}/{id_category}")
    public ProductPageDto findAllProductValidByInstituteByCategory(
            @PathVariable String id_institute,
            @PathVariable String id_category,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByInstituteByCategory(id_institute, id_category, page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public ProductDetailDto findOneProduct(@PathVariable String id) {
        return service.detail(id);
    }

    @PostMapping("/register")
    public ProductDetailDto registerOneProduct(
            @Validated(GroupValidation.Create.class) @RequestBody ProductSaveDto newProduct) {
        return service.save(newProduct);
    }

    @PatchMapping("/update/{id}")
    public ProductDetailDto patchOneProduct(@RequestBody ProductSaveDto newProduct, @PathVariable String id) {
        return service.update(newProduct, id);
    }

    @GetMapping("/toggle/activity/{id}")
    public ProductDetailDto patchActivedByOneProduct(@PathVariable String id) {
        return service.toggleActivity(id);
    }

}
