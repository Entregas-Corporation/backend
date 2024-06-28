package br.com.entregas.Entregas.modules.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOrder {
    REQUESTED("requested"),
    SENT("sent"),
    CANCELED("canceled"),
    DELIVERED("delivered");
    
    private String status;
}
