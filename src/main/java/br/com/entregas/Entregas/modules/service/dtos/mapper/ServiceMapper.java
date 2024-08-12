package br.com.entregas.Entregas.modules.service.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.modules.service.dtos.ServiceDetailDto;
import br.com.entregas.Entregas.modules.service.dtos.ServiceSaveDto;
import br.com.entregas.Entregas.modules.service.enums.ServiceMode;
import br.com.entregas.Entregas.modules.service.models.ServiceModel;


@Component
public class ServiceMapper {

    /* @Value("${file.upload-dir.service}")
    private String uploadDir; */

    public ServiceSaveDto toDto(ServiceModel service,  MultipartFile file) {
        if (service == null) {
            return null;
        }
/* 
        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadLocalConfig.upload(uploadDir, storageFileName, service.getImage(), file);
            service.setImage(storageFileName);
        } */

        return new ServiceSaveDto(
                service.getId(),
                service.getName(),
                service.getDescription(),
                file,
                service.getMode(),
                service.getPrice(),
                service.getInstitute(),
                service.getActived(),
                service.getValid()
                );
    }

    public ServiceDetailDto toDtoDetail(ServiceModel service) {
        if (service == null) {
            return null;
        }
        return new ServiceDetailDto(
            service.getId(),
                service.getName(),
                service.getDescription(),
                service.getImage(),
                service.getMode(),
                service.getPrice(),
                service.getInstitute().getName(),
                service.getCreated(), 
                service.getUpdated());
    }

    public ServiceModel toEntity(ServiceSaveDto serviceDto) {
        if (serviceDto == null) {
            return null;
        }

        ServiceModel service = new ServiceModel();

        if (serviceDto.id() != null) {
            service.setId(serviceDto.id());
        }
        if (serviceDto.mode().equals(ServiceMode.COMBINED)) {
            service.setPrice(0.0);            
        }else{
            service.setPrice(serviceDto.price());
        }

/*         MultipartFile file = serviceDto.image();

        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadLocalConfig.upload(uploadDir, storageFileName, service.getImage(), file);
            service.setImage(storageFileName);
        } */

        service.setName(serviceDto.name());
        service.setDescription(serviceDto.description());
        service.setMode(serviceDto.mode());
        service.setInstitute(serviceDto.institute());
        service.setActived(serviceDto.actived());
        service.setValid(serviceDto.valid());
        service.setUpdated(LocalDateTime.now());

        return service;
    }
}
