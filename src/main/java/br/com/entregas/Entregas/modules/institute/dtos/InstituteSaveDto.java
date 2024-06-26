package br.com.entregas.Entregas.modules.institute.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InstituteSaveDto(
      String id,
      @NotBlank(groups = GroupValidation.Create.class) @Size(max = 255) String name,
      @NotBlank(groups = GroupValidation.Create.class) String description,
      @NotBlank(groups = GroupValidation.Create.class) String image,
      @NotBlank(groups = {
            GroupValidation.Create.class }) @Size(max = 255) String city,
      @NotBlank(groups = { GroupValidation.Create.class }) @Size(max = 255) String longitude,
      @NotBlank(groups = { GroupValidation.Create.class }) @Size(max = 255) String latitude,
      @NotBlank(groups = { GroupValidation.Create.class }) @Size(max = 11) String whatsapp,
      @NotNull(groups = { GroupValidation.Create.class }) Double freight_cost_km,
      @NotNull(groups = { GroupValidation.Create.class }) UserModel user,
      Boolean actived,
      Boolean valid
      ){
}
