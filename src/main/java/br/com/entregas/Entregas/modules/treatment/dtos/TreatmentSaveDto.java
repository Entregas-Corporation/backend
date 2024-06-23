package br.com.entregas.Entregas.modules.treatment.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.treatment.enums.TreatmentType;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TreatmentSaveDto(
      String id,
      @NotBlank(groups = GroupValidation.Create.class) @Size(max = 255) String title,
      @NotBlank(groups = GroupValidation.Create.class) String subject,
      @NotNull(groups = GroupValidation.Create.class) TreatmentType type,
      @NotNull(groups = { GroupValidation.Create.class }) UserModel sender,
      InstituteModel institute,
      Boolean actived){
}
