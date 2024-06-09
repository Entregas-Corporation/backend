package br.com.entregas.Entregas.modules.product.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.productCategory.models.ProductCategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductSaveDto(
            String id,
            @NotBlank(groups = GroupValidation.Create.class) @Size(max = 255) String name,
            @NotBlank(groups = GroupValidation.Create.class) String description,
            @NotNull(groups = GroupValidation.Create.class) String image,
            @NotNull(groups = {GroupValidation.Create.class}) Double price,
            @NotNull(groups = {GroupValidation.Create.class}) Integer quantity,
            @NotNull(groups = {GroupValidation.Create.class}) InstituteModel institute,
            @NotNull(groups = {GroupValidation.Create.class}) ProductCategoryModel category,
            Boolean actived) {
}
