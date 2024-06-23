package br.com.entregas.Entregas.modules.institute.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.institute.dtos.InstituteDetailDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteSaveDto;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;

@Component
public class InstituteMapper {
    public InstituteSaveDto toDto(InstituteModel institute) {
        if (institute == null) {
            return null;
        }
        return new InstituteSaveDto(institute.getId(), 
        institute.getName(), 
        institute.getDescription(), 
        institute.getImage(),
         institute.getCity(),
          institute.getLongitude(), 
          institute.getLatitude(),
           institute.getWhatsapp(),
            institute.getFreight_cost_km(), 
            institute.getUser(), 
            institute.getActived());
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
        institute.setName(instituteDto.name());
        institute.setDescription(instituteDto.description());
        institute.setCity(instituteDto.city());
        institute.setFreight_cost_km(instituteDto.freight_cost_km());
        institute.setImage(instituteDto.image());
        institute.setLatitude(instituteDto.latitude());
        institute.setLongitude(instituteDto.longitude());
        institute.setWhatsapp(instituteDto.whatsapp());
        institute.setUser(instituteDto.user());
        institute.setActived(instituteDto.actived());
        institute.setUpdated(LocalDateTime.now());

        return institute;
    }
}
