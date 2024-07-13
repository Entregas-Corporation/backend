package br.com.entregas.Entregas.modules.service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
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
    private InstituteRepository instituteRepository;

    public ServicePageDto pageableService(int page, int pageSize, List<ServiceDetailDto> serviceList) {

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, serviceList.size());

        List<ServiceDetailDto> pageContent = serviceList.subList(startIndex, endIndex);

        Page<ServiceDetailDto> servicePage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                serviceList.size());

        return new ServicePageDto(servicePage.getContent(), servicePage.getTotalElements(),
                servicePage.getTotalPages());
    }

    @Transactional
    public ServicePageDto listValid(int page, int pageSize) {
        List<ServiceDetailDto> serviceList = repository.findAll().stream()
                .filter(service -> instituteRepository.findById(service.getInstitute().getId()).get().getValid()
                        .equals(true) && service.getActived().equals(true) && service.getValid().equals(true))
                .map(service -> mapper.toDtoDetail(service)).collect(Collectors.toList());

        return pageableService(page, pageSize, serviceList);
    }

    @Transactional
    public ServicePageDto listInvalid(int page, int pageSize) {
        List<ServiceDetailDto> serviceList = repository.findAll().stream()
                .filter(service -> instituteRepository.findById(service.getInstitute().getId()).get().getValid()
                .equals(true) && service.getActived().equals(false) && service.getValid().equals(true))
                .map(service -> mapper.toDtoDetail(service)).collect(Collectors.toList());

        return pageableService(page, pageSize, serviceList);
    }

    @Transactional
    public ServicePageDto listValidByInstitute(String idInstitute, int page, int pageSize) {
        List<ServiceDetailDto> serviceList = repository.findByInstituteId(idInstitute).stream()
                .filter(service -> instituteRepository.findById(service.getInstitute().getId()).get().getValid()
                .equals(true) && service.getActived().equals(true) && service.getValid().equals(true))
                .map(service -> mapper.toDtoDetail(service)).collect(Collectors.toList());

        return pageableService(page, pageSize, serviceList);
    }

    @Transactional
    public ServicePageDto listInvalidByInstitute(String idInstitute, int page, int pageSize) {
        List<ServiceDetailDto> serviceList = repository.findByInstituteId(idInstitute).stream()
                .filter(service -> instituteRepository.findById(service.getInstitute().getId()).get().getValid()
                .equals(true) && service.getActived().equals(false) && service.getValid().equals(true))
                .map(service -> mapper.toDtoDetail(service)).collect(Collectors.toList());

        return pageableService(page, pageSize, serviceList);
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
                service.image(),
                service.mode(),
                service.price(),
                service.institute(),
                true,
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
        }).map(inst -> mapper.toDto(inst, service.image()))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Serviço")))));
    }

    @Transactional
    public ServiceDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(Service -> mapper.toDto(Service, null))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Serviço")))));
    }
}
