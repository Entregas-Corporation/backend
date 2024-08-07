package br.com.entregas.Entregas.modules.institute.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.core.config.UploadConfig;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteDetailDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteSaveDto;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;

@Component
public class InstituteMapper {

    @Value("${file.upload-dir.institute}")
    private String uploadDir;

    public InstituteSaveDto toDto(InstituteModel institute, MultipartFile file) {
        if (institute == null) {
            return null;
        }

        InstituteSaveDto newInstitute = new InstituteSaveDto(
                institute.getId(),
                institute.getName(),
                institute.getDescription(),
                file,
                institute.getCity(),
                institute.getNumber(),
                institute.getComplement(),
                institute.getLongitude(),
                institute.getLatitude(),
                institute.getWhatsapp(),
                institute.getFreight_cost_km(),
                institute.getUser(),
                institute.getActived(),
                institute.getValid());

        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadConfig.upload(uploadDir, storageFileName, institute.getImage(), file);
            institute.setImage(storageFileName);
        }

        return newInstitute;
    }

    public InstituteDetailDto toDtoDetail(InstituteModel institute) {
        if (institute == null) {
            return null;
        }
        return new InstituteDetailDto(
                institute.getId(),
                institute.getName(),
                institute.getDescription(),
                institute.getImage(),
                institute.getCity(),
                institute.getNumber(),
                institute.getComplement(),
                institute.getLongitude(),
                institute.getLatitude(),
                institute.getWhatsapp(),
                institute.getFreight_cost_km(),
                institute.getUser().getName(),
                institute.getCreated(),
                institute.getUpdated());
    }

    public InstituteModel toEntity(InstituteSaveDto instituteDto) {
        if (instituteDto == null) {
            return null;
        }

        InstituteModel institute = new InstituteModel();

        if (instituteDto.id() != null) {
            institute.setId(instituteDto.id());
        }

        MultipartFile file = instituteDto.image();

        institute.setName(instituteDto.name());
        institute.setDescription(instituteDto.description());
        institute.setCity(instituteDto.city());
        institute.setNumber(instituteDto.number());
        institute.setComplement(instituteDto.complement());
        institute.setFreight_cost_km(instituteDto.freight_cost_km());
        institute.setLatitude(instituteDto.latitude());
        institute.setLongitude(instituteDto.longitude());
        institute.setWhatsapp(instituteDto.whatsapp());
        institute.setUser(instituteDto.user());
        institute.setActived(instituteDto.actived());
        institute.setValid(instituteDto.valid());
        institute.setUpdated(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadConfig.upload(uploadDir, storageFileName, institute.getImage(), file);
            institute.setImage(storageFileName);
        }

        return institute;
    }
}
