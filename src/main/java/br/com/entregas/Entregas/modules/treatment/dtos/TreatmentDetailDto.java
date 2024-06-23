package br.com.entregas.Entregas.modules.treatment.dtos;

import java.time.LocalDateTime;
import br.com.entregas.Entregas.modules.treatment.enums.TreatmentType;


public record TreatmentDetailDto(
        String id,
        String title,
        String subject,
        TreatmentType type,
        String senderName,
        String senderEmail,
        String instituteId,
        LocalDateTime created,
        LocalDateTime updated) {
}
