package br.com.entregas.Entregas.modules.institute.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.core.services.upload.CloudinaryUploadService;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteDetailDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstitutePageDto;
import br.com.entregas.Entregas.modules.institute.dtos.InstituteSaveDto;
import br.com.entregas.Entregas.modules.institute.dtos.mapper.InstituteMapper;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.service.repositories.ServiceRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstituteService {
    private InstituteRepository repository;
    private InstituteMapper mapper;
    private ServiceRepository serviceRepository;
    private ProductRepository productRepository;
    private CloudinaryUploadService cloudinaryUploadService;

    public InstitutePageDto pageableInstitute(int page, int pageSize, List<InstituteDetailDto> instituteList) {

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, instituteList.size());

        List<InstituteDetailDto> pageContent = instituteList.subList(startIndex, endIndex);

        Page<InstituteDetailDto> institutePage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                instituteList.size());

        return new InstitutePageDto(institutePage.getContent(), institutePage.getTotalElements(),
                institutePage.getTotalPages());
    }

    @Transactional
    public InstitutePageDto listValid(int page, int pageSize) {

        List<InstituteDetailDto> instituteList = repository.findAll().stream()
                .filter(institute -> institute.getActived().equals(true) && institute.getValid().equals(true))
                .map(institute -> mapper.toDtoDetail(institute)).collect(Collectors.toList());

        return pageableInstitute(page, pageSize, instituteList);
    }

    @Transactional
    public InstitutePageDto listInvalid(int page, int pageSize) {
        List<InstituteDetailDto> instituteList = repository.findAll().stream()
                .filter(institute -> institute.getActived().equals(false) && institute.getValid().equals(true))
                .map(institute -> mapper.toDtoDetail(institute)).collect(Collectors.toList());

        return pageableInstitute(page, pageSize, instituteList);
    }

    @Transactional
    public InstitutePageDto listValidByUser(String idUser, int page, int pageSize) {
        List<InstituteDetailDto> instituteList = repository.findByUserId(idUser).stream()
                .filter(institute -> institute.getUser().getId().equals(idUser) && institute.getActived().equals(true))
                .map(institute -> mapper.toDtoDetail(institute)).collect(Collectors.toList());

        return pageableInstitute(page, pageSize, instituteList);
    }

    @Transactional
    public InstitutePageDto listInvalidByUser(String idUser, int page, int pageSize) {
        List<InstituteDetailDto> instituteList = repository.findByUserId(idUser).stream()
                .filter(institute -> institute.getUser().getId().equals(idUser) && institute.getActived().equals(false))
                .map(institute -> mapper.toDtoDetail(institute)).collect(Collectors.toList());

        return pageableInstitute(page, pageSize, instituteList);
    }

    @Transactional
    public InstituteDetailDto detail(String id) {

        return repository.findById(id).map(institute -> mapper.toDtoDetail(institute))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Estabelecimento")));
    }

    @SuppressWarnings("null")
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
                institute.number(),
                institute.complement(),
                institute.longitude(),
                institute.latitude(),
                institute.whatsapp(),
                institute.freight_cost_km(),
                institute.user(),
                true,
                true);

        boolean erro = false;

        InstituteModel recordFound = null;
        try {
            recordFound = repository.save(mapper.toEntity(newInstitue));
        } catch (Exception e) {
            erro = true;
        } finally {
            if (!erro) {
                MultipartFile file = institute.image();
                String imageUrl = cloudinaryUploadService.uploadFile(file, newInstitue.id());
                recordFound.setImage(imageUrl);
                repository.save(recordFound);
            }
        }

        return mapper.toDtoDetail(recordFound);
    }

    @SuppressWarnings({ "finally", "null" })
    @Transactional
    public InstituteDetailDto update(InstituteSaveDto institute, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            if (institute.name() != null) {
                recordFound.setName(institute.name());
            }
            if (institute.city() != null) {
                recordFound.setCity(institute.city());
            }
            if (institute.number() != null) {
                recordFound.setNumber(institute.number());
            }
            if (institute.complement() != null) {
                recordFound.setComplement(institute.complement());
            }
            if (institute.description() != null) {
                recordFound.setDescription(institute.description());
            }
            if (institute.freight_cost_km() != null) {
                recordFound.setFreight_cost_km(institute.freight_cost_km());
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

            InstituteModel newRecordFound = null;
            boolean error = false;
            try {
                newRecordFound = repository.save(recordFound);
            } catch (Exception e) {
                error = true;
            } finally {
                if (!error) {
                    MultipartFile file = institute.image();
                    if (file != null && !file.isEmpty()) {

                        cloudinaryUploadService.deleteFile(recordFound.getId());
                        String imageUrl = cloudinaryUploadService.uploadFile(file, recordFound.getId());
                        recordFound.setImage(imageUrl);
                    }
                }
                recordFound.setUpdated(LocalDateTime.now());
                return repository.save(newRecordFound);
            }

        }).map(inst -> mapper.toDto(inst, institute.image()))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Estabelecimento")))));
    }

    @Transactional
    public InstituteDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    for (int i = 0; i < recordFound.getServices().size(); i++) {
                        serviceRepository.save(recordFound.getServices().get(i))
                                .setValid(!recordFound.getServices().get(i).getValid());
                    }
                    for (int i = 0; i < recordFound.getProducts().size(); i++) {
                        productRepository.save(recordFound.getProducts().get(i))
                                .setValid(!recordFound.getProducts().get(i).getValid());
                    }
                    return repository.save(recordFound);
                }).map(institute -> mapper.toDto(institute, null))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Estabelecimento")))));
    }
}
