package br.com.entregas.Entregas.modules.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceMode {
    COMBINED("combined"),
    PER_HOUR("per_hour"),
    BY_SERVICE("by_service");

    private String mode;
}
