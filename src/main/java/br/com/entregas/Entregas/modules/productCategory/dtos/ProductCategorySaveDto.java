package br.com.entregas.Entregas.modules.productCategory.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

public record ProductCategorySaveDto(
            String id,
            @NotBlank(groups = GroupValidation.Create.class) @Size(max = 255) String name,
            Boolean actived) {
}
