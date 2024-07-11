package br.com.entregas.Entregas.modules.deliveryman.services;

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
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanDetailDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanPageDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.DeliverymanSaveDto;
import br.com.entregas.Entregas.modules.deliveryman.dtos.mapper.DeliverymanMapper;
import br.com.entregas.Entregas.modules.deliveryman.repositories.DeliverymanRepository;
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
import br.com.entregas.Entregas.modules.user.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliverymanService {
    private DeliverymanRepository repository;
    private DeliverymanMapper mapper;
    private InstituteRepository instituteRepository;
    private UserRepository userRepository;

    public DeliverymanPageDto pageableDeliveryman(int page, int pageSize, List<DeliverymanDetailDto> deliverymanList) {

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, deliverymanList.size());

        List<DeliverymanDetailDto> pageContent = deliverymanList.subList(startIndex, endIndex);

        Page<DeliverymanDetailDto> deliverymanPage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                deliverymanList.size());

        return new DeliverymanPageDto(deliverymanPage.getContent(), deliverymanPage.getTotalElements(),
                deliverymanPage.getTotalPages());
    }

    @Transactional
    public DeliverymanPageDto listValid(int page, int pageSize) {
        List<DeliverymanDetailDto> deliverymanList = repository.findAll().stream()
                .filter(deliveryman -> instituteRepository.findById(deliveryman.getInstitute().getId()).get().getValid()
                        .equals(true)
                        && userRepository.findById(deliveryman.getUser().getId()).get().getActived().equals(true)
                        && deliveryman.getActived().equals(true) && deliveryman.getValid().equals(true))
                .map(deliveryman -> mapper.toDtoDetail(deliveryman)).collect(Collectors.toList());

        return pageableDeliveryman(page, pageSize, deliverymanList);
    }

    @Transactional
    public DeliverymanPageDto listInvalid(int page, int pageSize) {
        List<DeliverymanDetailDto> deliverymanList = repository.findAll().stream()
                .filter(deliveryman -> instituteRepository.findById(deliveryman.getInstitute().getId()).get().getValid()
                        .equals(true)
                        && userRepository.findById(deliveryman.getUser().getId()).get().getActived().equals(true)
                        && deliveryman.getActived().equals(false) && deliveryman.getValid().equals(true))
                .map(deliveryman -> mapper.toDtoDetail(deliveryman)).collect(Collectors.toList());

        return pageableDeliveryman(page, pageSize, deliverymanList);
    }

    @Transactional
    public DeliverymanPageDto listValidByInstitute(String idInstitute, int page, int pageSize) {
        List<DeliverymanDetailDto> deliverymanList = repository.findByInstituteId(idInstitute).stream()
                .filter(deliveryman -> instituteRepository.findById(deliveryman.getInstitute().getId()).get().getValid()
                        .equals(true)
                        && userRepository.findById(deliveryman.getUser().getId()).get().getActived().equals(true)
                        && deliveryman.getActived().equals(true) && deliveryman.getValid().equals(true))
                .map(deliveryman -> mapper.toDtoDetail(deliveryman)).collect(Collectors.toList());

        return pageableDeliveryman(page, pageSize, deliverymanList);
    }

    @Transactional
    public DeliverymanPageDto listInvalidByInstitute(String idInstitute, int page, int pageSize) {
        List<DeliverymanDetailDto> deliverymanList = repository.findByInstituteId(idInstitute).stream()
                .filter(deliveryman -> instituteRepository.findById(deliveryman.getInstitute().getId()).get().getValid()
                        .equals(true)
                        && userRepository.findById(deliveryman.getUser().getId()).get().getActived().equals(true)
                        && deliveryman.getActived().equals(false) && deliveryman.getValid().equals(true))
                .map(deliveryman -> mapper.toDtoDetail(deliveryman)).collect(Collectors.toList());

        return pageableDeliveryman(page, pageSize, deliverymanList);
    }

    @Transactional
    public DeliverymanPageDto listValidByUser(String idUser, int page, int pageSize) {
        List<DeliverymanDetailDto> deliverymanList = repository.findByUserId(idUser).stream()
                .filter(deliveryman -> instituteRepository.findById(deliveryman.getInstitute().getId()).get().getValid()
                        .equals(true)
                        && userRepository.findById(deliveryman.getUser().getId()).get().getActived().equals(true)
                        && deliveryman.getActived().equals(true) && deliveryman.getValid().equals(true))
                .map(deliveryman -> mapper.toDtoDetail(deliveryman)).collect(Collectors.toList());

        return pageableDeliveryman(page, pageSize, deliverymanList);
    }

    @Transactional
    public DeliverymanPageDto listInvalidByUser(String idUser, int page, int pageSize) {
        List<DeliverymanDetailDto> deliverymanList = repository.findByUserId(idUser).stream()
                .filter(deliveryman -> instituteRepository.findById(deliveryman.getInstitute().getId()).get().getValid()
                        .equals(true)
                        && userRepository.findById(deliveryman.getUser().getId()).get().getActived().equals(true)
                        && deliveryman.getActived().equals(false) && deliveryman.getValid().equals(true))
                .map(deliveryman -> mapper.toDtoDetail(deliveryman)).collect(Collectors.toList());

        return pageableDeliveryman(page, pageSize, deliverymanList);
    }

    @Transactional
    public DeliverymanDetailDto detail(String id) {
        return repository.findById(id).map(Deliveryman -> mapper.toDtoDetail(Deliveryman))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Entregador")));
    }

    @Transactional
    public DeliverymanDetailDto save(DeliverymanSaveDto deliveryman) {
        DeliverymanSaveDto newDeliveryman = new DeliverymanSaveDto(
                deliveryman.id(),
                deliveryman.curriculum(),
                deliveryman.institute(),
                deliveryman.user(),
                false,
                true);
        return mapper.toDtoDetail(repository.save(mapper.toEntity(newDeliveryman)));
    }

    @Transactional
    public DeliverymanDetailDto update(DeliverymanSaveDto deliveryman, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            recordFound.setUpdated(LocalDateTime.now());
            return repository.save(recordFound);
        }).map(inst -> mapper.toDto(inst, deliveryman.curriculum()))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Entregador")))));
    }

    @Transactional
    public DeliverymanDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(deliveryman -> mapper.toDto(deliveryman, null))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Entregador")))));
    }
}
