package br.com.entregas.Entregas.modules.treatment.services;

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
import br.com.entregas.Entregas.core.services.sendEmail.SendEmailService;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentDetailDto;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentPageDto;
import br.com.entregas.Entregas.modules.treatment.dtos.TreatmentSaveDto;
import br.com.entregas.Entregas.modules.treatment.dtos.mapper.TreatmentMapper;
import br.com.entregas.Entregas.modules.treatment.enums.TreatmentType;
import br.com.entregas.Entregas.modules.treatment.repositories.TreatmentRepository;
import br.com.entregas.Entregas.modules.user.dtos.UserDetailDto;
import br.com.entregas.Entregas.modules.user.dtos.mapper.UserMapper;
import br.com.entregas.Entregas.modules.user.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TreatmentService {
    private TreatmentRepository repository;
    private TreatmentMapper mapper;
    private SendEmailService sendEmailService;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public TreatmentPageDto pageableTreatment(int page, int pageSize, List<TreatmentDetailDto> treatmentList) {

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, treatmentList.size());

        List<TreatmentDetailDto> pageContent = treatmentList.subList(startIndex, endIndex);

        Page<TreatmentDetailDto> treatmentPage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                treatmentList.size());

        return new TreatmentPageDto(treatmentPage.getContent(), treatmentPage.getTotalElements(),
                treatmentPage.getTotalPages());
    }

    @Transactional
    public TreatmentPageDto listResolvedSupport(int page, int pageSize) {

        List<TreatmentDetailDto> treatmentList = repository.findAll().stream()
                .filter(treatment -> treatment.getActived().equals(true)
                        && treatment.getType().equals(TreatmentType.SUPPORT))
                .map(treatment -> mapper.toDtoDetail(treatment)).collect(Collectors.toList());

        return pageableTreatment(page, pageSize, treatmentList);
    }

    @Transactional
    public TreatmentPageDto listNotResolvedSupport(int page, int pageSize) {
        List<TreatmentDetailDto> treatmentList = repository.findAll().stream()
                .filter(treatment -> treatment.getActived().equals(false)
                        && treatment.getType().equals(TreatmentType.SUPPORT))
                .map(treatment -> mapper.toDtoDetail(treatment)).collect(Collectors.toList());

        return pageableTreatment(page, pageSize, treatmentList);
    }

    @Transactional
    public TreatmentPageDto listResolvedComplaint(int page, int pageSize) {
        List<TreatmentDetailDto> treatmentList = repository.findAll().stream()
                .filter(treatment -> treatment.getActived().equals(true)
                        && treatment.getType().equals(TreatmentType.COMPLAINT))
                .map(treatment -> mapper.toDtoDetail(treatment)).collect(Collectors.toList());

        return pageableTreatment(page, pageSize, treatmentList);
    }

    @Transactional
    public TreatmentPageDto listNotResolvedComplaint(int page, int pageSize) {
        List<TreatmentDetailDto> treatmentList = repository.findAll().stream()
                .filter(treatment -> treatment.getActived().equals(false)
                        && treatment.getType().equals(TreatmentType.COMPLAINT))
                .map(treatment -> mapper.toDtoDetail(treatment)).collect(Collectors.toList());

        return pageableTreatment(page, pageSize, treatmentList);
    }

    @Transactional
    public TreatmentPageDto listResolvedComplaintByInstitute(String idInstitute, int page, int pageSize) {
        List<TreatmentDetailDto> treatmentList = repository.findByInstituteId(idInstitute).stream()
                .filter(treatment -> treatment.getActived().equals(true)
                        && treatment.getInstitute().getId().equals(idInstitute))
                .map(treatment -> mapper.toDtoDetail(treatment)).collect(Collectors.toList());

        return pageableTreatment(page, pageSize, treatmentList);
    }

    @Transactional
    public TreatmentPageDto listNotResolvedComplaintByInstitute(String idInstitute, int page, int pageSize) {
        
        List<TreatmentDetailDto> treatmentList = repository.findByInstituteId(idInstitute).stream()
                .filter(treatment -> treatment.getActived().equals(false)
                        && treatment.getInstitute().getId().equals(idInstitute))
                .map(treatment -> mapper.toDtoDetail(treatment)).collect(Collectors.toList());

        return pageableTreatment(page, pageSize, treatmentList);
    }

    @Transactional
    public TreatmentDetailDto detail(String id) {
        return repository.findById(id).map(treatment -> mapper.toDtoDetail(treatment))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Atendimento")));
    }

    @Transactional
    public TreatmentDetailDto save(TreatmentSaveDto treatment) {
        if (treatment.type().equals(TreatmentType.COMPLAINT) && treatment.institute() == null) {
            throw new DomainException(ExceptionMessageConstant.notFound("Estabelecimento"));
        }
        UserDetailDto userTreatment = userRepository.findById(treatment.sender().getId())
                .map(user -> userMapper.toDtoDetail(user)).get();
        TreatmentSaveDto newTreatment = new TreatmentSaveDto(
                treatment.id(),
                treatment.title(),
                treatment.subject(),
                treatment.type(),
                treatment.sender(),
                treatment.institute(),
                false);
        TreatmentSaveDto saveTreatment = mapper.toDto(repository.save(mapper.toEntity(newTreatment)));
        if (saveTreatment.type().equals(TreatmentType.SUPPORT)) {
            sendEmailService.sendTreatmentBySupport(userTreatment);
        } else if (saveTreatment.type().equals(TreatmentType.COMPLAINT)) {
            sendEmailService.sendTreatmentByComplaint(userTreatment);
        }
        return mapper.toDtoDetail(mapper.toEntity(saveTreatment));
    }

    @Transactional
    public TreatmentDetailDto toggleStatusResolved(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(Treatment -> mapper.toDto(Treatment))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Atendimento")))));
    }
}
