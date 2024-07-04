package br.com.entregas.Entregas.modules.delivery.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.delivery.dtos.DeliveryDetailDto;
import br.com.entregas.Entregas.modules.delivery.dtos.DeliverySaveDto;
import br.com.entregas.Entregas.modules.delivery.models.DeliveryModel;


@Component
public class DeliveryMapper {
    public DeliverySaveDto toDto(DeliveryModel delivery) {
        if (delivery == null) {
            return null;
        }
        return new DeliverySaveDto(
            delivery.getId(), delivery.getDeliveryman(), delivery.getOrder());
    }

    public DeliveryDetailDto toDtoDetail(DeliveryModel delivery) {
        if (delivery == null) {
            return null;
        }
        return new DeliveryDetailDto(
            delivery.getId(), delivery.getDeliveryman().getId(), delivery.getOrder().getId(), delivery.getCreated(), delivery.getUpdated());
    }

    public DeliveryModel toEntity(DeliverySaveDto deliveryDto) {
        if (deliveryDto == null) {
            return null;
        }

        DeliveryModel delivery = new DeliveryModel();

        if (deliveryDto.id() != null) {
            delivery.setId(deliveryDto.id());
        }
        delivery.setDeliveryman(deliveryDto.deliveryman());
        delivery.setOrder(deliveryDto.order());
        delivery.setUpdated(LocalDateTime.now());

        return delivery;
    }
}
