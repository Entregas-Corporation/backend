package br.com.entregas.Entregas.modules.institute.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteDetailDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstitutePageDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteSaveDto;
import br.com.entregas.Entregas.modules.institute.dtos.mapper.InstituteMapper;
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstituteService {
    private InstituteRepository repository;
    private InstituteMapper mapper;

    @Transactional
    public InstitutePageDto listValid(int page, int pageSize) {
        Page<InstituteDetailDto> institutePage = repository.findByActivedTrue(PageRequest.of(page, pageSize))
                .map(institute -> mapper.toDtoDetail(institute));
        return new InstitutePageDto(institutePage.getContent(), institutePage.getTotalElements(),
                institutePage.getTotalPages());
    }

    @Transactional
    public InstitutePageDto listInvalid(int page, int pageSize) {
        Page<InstituteDetailDto> institutePage = repository.findByActivedFalse(PageRequest.of(page, pageSize))
                .map(institute -> mapper.toDtoDetail(institute));
        return new InstitutePageDto(institutePage.getContent(), institutePage.getTotalElements(),
                institutePage.getTotalPages());
    }

    @Transactional
    public InstitutePageDto listValidByUser(String idUser, int page, int pageSize) {
        Page<InstituteDetailDto> institutePage = repository.findByActivedTrue(PageRequest.of(page, pageSize))
                .map(institute -> {
                    if (institute.getUser().getId().equals(idUser)) {
                        return mapper.toDtoDetail(institute);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Usuário"));
                });
        return new InstitutePageDto(institutePage.getContent(), institutePage.getTotalElements(),
                institutePage.getTotalPages());
    }

    @Transactional
    public InstitutePageDto listInvalidByUser(String idUser, int page, int pageSize) {
        Page<InstituteDetailDto> institutePage = repository.findByActivedFalse(PageRequest.of(page, pageSize))
                .map(institute -> {
                    if (institute.getUser().getId().equals(idUser)) {
                        return mapper.toDtoDetail(institute);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Usuário"));
                });
        return new InstitutePageDto(institutePage.getContent(), institutePage.getTotalElements(),
                institutePage.getTotalPages());
    }

    @Transactional
    public InstituteDetailDto detail(String id) {

        return repository.findById(id).map(institute -> mapper.toDtoDetail(institute))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Estabelecimento")));
    }

    @Transactional
    public InstituteDetailDto save(InstituteSaveDto institute) {
        boolean whatsappUsed = repository.findByWhatsapp(mapper.toEntity(institute).getWhatsapp()).isPresent();
        if (whatsappUsed) {
            throw new DomainException(ExceptionMessageConstant.attributeUsed("Whatsapp"));
        }
        InstituteSaveDto newInstitue = new InstituteSaveDto(
                institute.id(),
                institute.name(),
                institute.description(),
                institute.image(),
                institute.city(),
                institute.longitude(),
                institute.latitude(),
                institute.whatsapp(),
                institute.freight_cost_km(),
                institute.user(),
                true);
        return mapper.toDtoDetail(repository.save(mapper.toEntity(newInstitue)));
    }

    @Transactional
    public InstituteDetailDto update(InstituteSaveDto institute, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            if (institute.name() != null) {
                recordFound.setName(institute.name());
            }
            if (institute.city() != null) {
                recordFound.setCity(institute.city());
            }
            if (institute.description() != null) {
                recordFound.setDescription(institute.description());
            }
            if (institute.freight_cost_km() != null) {
                recordFound.setFreight_cost_km(institute.freight_cost_km());
            }
            if (institute.image() != null) {
                recordFound.setImage(institute.image());
            }
            if (institute.latitude() != null) {
                recordFound.setLatitude(institute.latitude());
            }
            if (institute.longitude() != null) {
                recordFound.setLongitude(institute.longitude());
            }
            if (institute.whatsapp() != null) {
                if (repository.findByWhatsapp(mapper.toEntity(institute).getWhatsapp()).isPresent()) {
                    throw new DomainException(ExceptionMessageConstant.attributeUsed("Whatsapp"));
                } else {
                    recordFound.setWhatsapp(institute.whatsapp());
                }
            }
            recordFound.setUpdated(LocalDateTime.now());
            return repository.save(recordFound);
        }).map(inst -> mapper.toDto(inst))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Estabelecimento")))));
    }

    @Transactional
    public InstituteDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(institute -> mapper.toDto(institute))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Estabelecimento")))));
    }
}
