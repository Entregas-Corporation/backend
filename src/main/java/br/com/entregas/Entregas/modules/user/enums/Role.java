package br.com.entregas.Entregas.modules.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("admin"),
    CUSTOMER("customer"),
    BUSINESSPERSON("businessperson");
    private String role;
}
