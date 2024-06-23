package br.com.entregas.Entregas.modules.treatment.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentDetailDto;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentSaveDto;
import br.com.entregas.Entregas.modules.treatment.enums.TreatmentType;
import br.com.entregas.Entregas.modules.treatment.models.TreatmentModel;

@Component
public class TreatmentMapper {
    public TreatmentSaveDto toDto(TreatmentModel treatment) {
        if (treatment == null) {
            return null;
        }
        return new TreatmentSaveDto(
                treatment.getId(),
                treatment.getTitle(),
                treatment.getSubject(),
                treatment.getType(),
                treatment.getSender(),
                treatment.getInstitute(),
                treatment.getActived());
    }

    public TreatmentDetailDto toDtoDetail(TreatmentModel treatment) {
        if (treatment == null) {
            return null;
        }
        if (treatment.getType().equals(TreatmentType.SUPPORT)) {
            return new TreatmentDetailDto(
                    treatment.getId(),
                    treatment.getTitle(),
                    treatment.getSubject(),
                    treatment.getType(),
                    treatment.getSender().getName(),
                    treatment.getSender().getEmail(),
                    null,
                    treatment.getCreated(),
                    treatment.getUpdated());

        }
        return new TreatmentDetailDto(
            treatment.getId(),
            treatment.getTitle(),
            treatment.getSubject(),
            treatment.getType(),
            treatment.getSender().getName(),
            treatment.getSender().getEmail(),
            treatment.getInstitute().getId(),
            treatment.getCreated(),
            treatment.getUpdated());
    }

    public TreatmentModel toEntity(TreatmentSaveDto treatmentDto) {
        if (treatmentDto == null) {
            return null;
        }

        TreatmentModel treatment = new TreatmentModel();

        if (treatmentDto.id() != null) {
            treatment.setId(treatmentDto.id());
        }
        treatment.setTitle(treatmentDto.title());
        treatment.setSubject(treatmentDto.subject());
        treatment.setInstitute(treatmentDto.institute());
        treatment.setSender(treatmentDto.sender());
        treatment.setType(treatmentDto.type());
        treatment.setActived(treatmentDto.actived());
        treatment.setUpdated(LocalDateTime.now());

        return treatment;
    }
}
