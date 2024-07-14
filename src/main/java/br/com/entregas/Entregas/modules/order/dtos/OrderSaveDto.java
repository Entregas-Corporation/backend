package br.com.entregas.Entregas.modules.order.dtos;

import java.util.Date;

import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.order.enums.StatusOrder;

public record OrderSaveDto(
                String id,
                StatusOrder status,
                Double freight,
                InstituteModel institute,
                String userName,
                String userEmail,
                Date date) {
}
