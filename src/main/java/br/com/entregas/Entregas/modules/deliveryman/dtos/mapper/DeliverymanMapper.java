package br.com.entregas.Entregas.modules.deliveryman.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanDetailDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanSaveDto;
import br.com.entregas.Entregas.modules.deliveryman.models.DeliverymanModel;

@Component
public class DeliverymanMapper {
    public DeliverymanSaveDto toDto(DeliverymanModel deliveryman) {
        if (deliveryman == null) {
            return null;
        }
        return new DeliverymanSaveDto(
                deliveryman.getId(),
                deliveryman.getCurriculum(),
                deliveryman.getInstitute(),
                deliveryman.getUser(),
                deliveryman.getActived(),
                deliveryman.getValid());
    }

    public DeliverymanDetailDto toDtoDetail(DeliverymanModel deliveryman) {
        if (deliveryman == null) {
            return null;
        }
        return new DeliverymanDetailDto(
                deliveryman.getId(),
                deliveryman.getCurriculum(),
                deliveryman.getInstitute().getName(),
                deliveryman.getUser().getName(),
                deliveryman.getUser().getEmail(),
                deliveryman.getCreated(),
                deliveryman.getUpdated());
    }

    public DeliverymanModel toEntity(DeliverymanSaveDto deliverymanDto) {
        if (deliverymanDto == null) {
            return null;
        }

        DeliverymanModel deliveryman = new DeliverymanModel();

        if (deliverymanDto.id() != null) {
            deliveryman.setId(deliverymanDto.id());
        }
        deliveryman.setCurriculum(deliverymanDto.curriculum());
        deliveryman.setInstitute(deliverymanDto.institute());
        deliveryman.setUser(deliverymanDto.user());
        deliveryman.setActived(deliverymanDto.actived());
        deliveryman.setValid(deliverymanDto.valid());
        deliveryman.setUpdated(LocalDateTime.now());

        return deliveryman;
    }
}
