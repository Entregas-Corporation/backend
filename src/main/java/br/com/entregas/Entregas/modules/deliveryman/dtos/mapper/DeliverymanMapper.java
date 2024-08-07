package br.com.entregas.Entregas.modules.deliveryman.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.core.config.UploadConfig;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanDetailDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanSaveDto;
import br.com.entregas.Entregas.modules.deliveryman.models.DeliverymanModel;

@Component
public class DeliverymanMapper {

    @Value("${file.upload-dir.deliveryman}")
    private String uploadDir;

    public DeliverymanSaveDto toDto(DeliverymanModel deliveryman, MultipartFile file) {
        if (deliveryman == null) {
            return null;
        }

        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadConfig.upload(uploadDir, storageFileName, deliveryman.getCurriculum(), file);
            deliveryman.setCurriculum(storageFileName);
        }

        return new DeliverymanSaveDto(
                deliveryman.getId(),
                file,
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
 
        MultipartFile file = deliverymanDto.curriculum();
        
        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadConfig.upload(uploadDir, storageFileName, deliveryman.getCurriculum(), file);
            deliveryman.setCurriculum(storageFileName);
        }
        deliveryman.setInstitute(deliverymanDto.institute());
        deliveryman.setUser(deliverymanDto.user());
        deliveryman.setActived(deliverymanDto.actived());
        deliveryman.setValid(deliverymanDto.valid());
        deliveryman.setUpdated(LocalDateTime.now());

        return deliveryman;
    }
}
