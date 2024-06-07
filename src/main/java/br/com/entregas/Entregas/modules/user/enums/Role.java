package br.com.entregas.Entregas.modules.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("admin"),
    USER("user");
    private String role;
}
