package br.com.entregas.Entregas.modules.deliveryman.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliverymanSaveDto(
            String id,
            @NotBlank(groups = GroupValidation.Create.class) String curriculum,
            @NotNull(groups = {GroupValidation.Create.class}) InstituteModel institute,
            @NotNull(groups = {GroupValidation.Create.class}) UserModel user,
            Boolean actived,
            Boolean valid
            ) {
}
