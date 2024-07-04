package br.com.entregas.Entregas.modules.delivery.dtos;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.deliveryman.models.DeliverymanModel;
import br.com.entregas.Entregas.modules.order.models.OrderModel;
import jakarta.validation.constraints.NotNull;

public record DeliverySaveDto(
      String id,
      @NotNull(groups = { GroupValidation.Create.class }) DeliverymanModel deliveryman,
      @NotNull(groups = { GroupValidation.Create.class }) OrderModel order
      ){
}
