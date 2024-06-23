package br.com.entregas.Entregas.modules.treatment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TreatmentType {
    SUPPORT("support"),
    COMPLAINT("complaint");
    
    private String type;
}
