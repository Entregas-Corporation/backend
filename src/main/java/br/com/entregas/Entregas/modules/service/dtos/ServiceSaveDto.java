package br.com.entregas.Entregas.modules.service.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.service.enums.ServiceMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceSaveDto(
            String id,
            @NotBlank(groups = GroupValidation.Create.class) @Size(max = 255) String name,
            @NotBlank(groups = GroupValidation.Create.class) String description,
            @NotNull(groups = GroupValidation.Create.class) ServiceMode mode,
            @NotNull(groups = {GroupValidation.Create.class}) Double price,
            @NotNull(groups = {GroupValidation.Create.class}) InstituteModel institute,
            Boolean actived,
            Boolean valid
            ) {
}
