package br.com.entregas.Entregas.modules.productItem.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.validation.constraints.NotNull;

public record ProductItemSaveDto(
        String id,
        @NotNull(groups = GroupValidation.Create.class) ProductModel product,
        @NotNull(groups = GroupValidation.Create.class) UserModel user,
        @NotNull(groups = GroupValidation.Create.class) Integer quantity,
        Double price,
        Boolean actived
        ){
}
