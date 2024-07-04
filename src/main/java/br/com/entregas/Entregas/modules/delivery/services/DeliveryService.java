package br.com.entregas.Entregas.modules.delivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.modules.delivery.dtos.DeliveryDetailDto;
import br.com.entregas.Entregas.modules.delivery.dtos.DeliveryPageDto;
import br.com.entregas.Entregas.modules.delivery.dtos.DeliverySaveDto;
import br.com.entregas.Entregas.modules.delivery.dtos.mapper.DeliveryMapper;
import br.com.entregas.Entregas.modules.delivery.repositories.DeliveryRepository;
import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.order.repositories.OrderRepository;
import br.com.entregas.Entregas.modules.order.services.OrderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryService {
    private DeliveryRepository repository;
    private OrderRepository orderRepository;
    private OrderService orderService;
    private DeliveryMapper mapper;

    public DeliveryPageDto pageableDelivery(int page, int pageSize, List<DeliveryDetailDto> deliveryList) {

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, deliveryList.size());

        List<DeliveryDetailDto> pageContent = deliveryList.subList(startIndex, endIndex);

        Page<DeliveryDetailDto> deliveryPage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                deliveryList.size());

        return new DeliveryPageDto(deliveryPage.getContent(), deliveryPage.getTotalElements(),
                deliveryPage.getTotalPages());
    }

    @Transactional
    public DeliveryDetailDto save(DeliverySaveDto delivery) {
        DeliverySaveDto newDelivery = new DeliverySaveDto(
                delivery.id(), delivery.deliveryman(), delivery.order());

        DeliveryDetailDto deliveryDetailDto = mapper.toDtoDetail(repository.save(mapper.toEntity(newDelivery)));
        orderService.send(orderRepository.findById(newDelivery.order().getId()).get().getId());

        return deliveryDetailDto;
    }

    @Transactional
    public List<DeliveryDetailDto> ListOrdersSentByDeliverymanId(String id) {
        List<DeliveryDetailDto> deliveryDetailDto = repository.findByDeliverymanId(id).stream()
        .filter(delivery -> orderRepository.findById(delivery.getOrder().getId()).get().getStatus().equals(StatusOrder.SENT))
                .map(delivery -> mapper.toDtoDetail(delivery)).collect(Collectors.toList());

        return deliveryDetailDto;

    }


    @Transactional
    public DeliveryPageDto ListOrdersCanceledByDeliverymanId(String id, int page, int pageSize) {
        List<DeliveryDetailDto> deliveryDetailDto = repository.findByDeliverymanId(id).stream()
        .filter(delivery -> orderRepository.findById(delivery.getOrder().getId()).get().getStatus().equals(StatusOrder.CANCELED))
                .map(delivery -> mapper.toDtoDetail(delivery)).collect(Collectors.toList());

        return pageableDelivery(page, pageSize, deliveryDetailDto);

    }

    @Transactional
    public DeliveryPageDto ListOrdersDeliveredtByDeliverymanId(String id, int page, int pageSize) {
        List<DeliveryDetailDto> deliveryDetailDto = repository.findByDeliverymanId(id).stream()
        .filter(delivery -> orderRepository.findById(delivery.getOrder().getId()).get().getStatus().equals(StatusOrder.DELIVERED))
                .map(delivery -> mapper.toDtoDetail(delivery)).collect(Collectors.toList());

        return pageableDelivery(page, pageSize, deliveryDetailDto);

    }


    @Transactional
    public List<DeliveryDetailDto> ListDeliverymenByOrder(String id) {
        List<DeliveryDetailDto> deliveryDetailDto = repository.findByOrderId(id).stream()
                .map(delivery -> mapper.toDtoDetail(delivery)).collect(Collectors.toList());

        return deliveryDetailDto;

    }
}
