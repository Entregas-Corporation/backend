package br.com.entregas.Entregas.modules.productItem.controllers;

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
import br.com.entregas.Entregas.modules.productItem.dtos.ProductItemDetailDto;
import br.com.entregas.Entregas.modules.productItem.dtos.ProductItemSaveDto;
import br.com.entregas.Entregas.modules.productItem.services.ProductItemService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-item")
@AllArgsConstructor
public class ProductItemController {
    private ProductItemService service;

    @GetMapping("/list/user/valid/{id}")
    public List<ProductItemDetailDto> findAllProductItemValidByUser(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int pageSize) {
        return service.listValidByUser(id);
    }

    @GetMapping("/detail/{id}")
    public ProductItemDetailDto findOneProductItem(@PathVariable String id) {
        return service.detail(id);
    }

    @PostMapping("/register")
    public ProductItemDetailDto registerOneProductItem(
            @Validated(GroupValidation.Create.class) @RequestBody ProductItemSaveDto newProductItem) {
        return service.save(newProductItem);
    }

    @PatchMapping("/update/{id}")
    public ProductItemDetailDto patchOneProductItem(@RequestBody ProductItemSaveDto newProductItem, @PathVariable String id) {
        return service.update(newProductItem, id);
    }

    @GetMapping("/delete/{id}")
    public void patchActivedByOneProductItem(@PathVariable String id) {
        service.toggleActivity(id);
    }

}