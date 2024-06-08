package br.com.entregas.Entregas.modules.service.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.service.dtos.ServiceDetailDto;
import br.com.entregas.Entregas.modules.service.dtos.ServicePageDto;
import br.com.entregas.Entregas.modules.service.dtos.ServiceSaveDto;
import br.com.entregas.Entregas.modules.service.dtos.mapper.ServiceMapper;
import br.com.entregas.Entregas.modules.service.enums.ServiceMode;
import br.com.entregas.Entregas.modules.service.repositories.ServiceRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceService {
    private ServiceRepository repository;
    private ServiceMapper mapper;

    @Transactional
    public ServicePageDto listValid(int page, int pageSize) {
        Page<ServiceDetailDto> servicePage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(service -> mapper.toDtoDetail(service));
        return new ServicePageDto(servicePage.getContent(), servicePage.getTotalElements(),
                servicePage.getTotalPages());
    }

    @Transactional
    public ServicePageDto listInvalid(int page, int pageSize) {
        Page<ServiceDetailDto> servicePage = repository
                .findByActivedFalse(PageRequest.of(page, pageSize))
                .map(service -> mapper.toDtoDetail(service));
        return new ServicePageDto(servicePage.getContent(), servicePage.getTotalElements(),
                servicePage.getTotalPages());
    }

    @Transactional
    public ServicePageDto listValidByInstitute(String idInstitute, int page, int pageSize) {
        Page<ServiceDetailDto> servicePage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(service -> {
                    if (service.getInstitute().getId().equals(idInstitute)) {
                        return mapper.toDtoDetail(service);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Estabelecimento"));
                });
        return new ServicePageDto(servicePage.getContent(), servicePage.getTotalElements(),
                servicePage.getTotalPages());
    }

    @Transactional
    public ServicePageDto listInvalidByInstitute(String idInstitute, int page, int pageSize) {
        Page<ServiceDetailDto> servicePage = repository
                .findByActivedFalse(PageRequest.of(page, pageSize))
                .map(service -> {
                    if (service.getInstitute().getId().equals(idInstitute)) {
                        return mapper.toDtoDetail(service);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Estabelecimento"));
                });
        return new ServicePageDto(servicePage.getContent(), servicePage.getTotalElements(),
                servicePage.getTotalPages());
    }

    @Transactional
    public ServiceDetailDto detail(String id) {
        return repository.findById(id).map(service -> mapper.toDtoDetail(service))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Serviço")));
    }

    @Transactional
    public ServiceDetailDto save(ServiceSaveDto service) {
        ServiceSaveDto newService = new ServiceSaveDto(
                service.id(),
                service.name(),
                service.description(),
                service.mode(),
                service.price(),
                service.institute(),
                true);
        return mapper.toDtoDetail(repository.save(mapper.toEntity(newService)));
    }

    @Transactional
    public ServiceDetailDto update(ServiceSaveDto service, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            if (service.name() != null) {
                recordFound.setName(service.name());
            }
            if (service.description() != null) {
                recordFound.setDescription(service.description());
            }
            if (service.mode() != null) {
                recordFound.setMode(service.mode());
            }
            if (service.price() != null && (service.mode() == null || service.mode() != ServiceMode.COMBINED)
                    && recordFound.getMode() != ServiceMode.COMBINED) {
                recordFound.setPrice(service.price());
            }
            recordFound.setUpdated(LocalDateTime.now());
            return repository.save(recordFound);
        }).map(inst -> mapper.toDto(inst))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Serviço")))));
    }

    @Transactional
    public ServiceDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(Service -> mapper.toDto(Service))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Serviço")))));
    }
}
