package br.com.entregas.Entregas.modules.order.services;

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
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
import br.com.entregas.Entregas.modules.order.dtos.OrderDetailDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderPageDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderSaveDto;
import br.com.entregas.Entregas.modules.order.dtos.mapper.OrderMapper;
import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.order.repositories.OrderRepository;
import br.com.entregas.Entregas.modules.orderItem.models.OrderItemModel;
import br.com.entregas.Entregas.modules.orderItem.repositories.OrderItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
        private OrderRepository repository;
        private OrderMapper mapper;
        private OrderItemRepository orderItemRepository;
        private InstituteRepository instituteRepository;
        private SendEmailService sendEmailService;
        public OrderPageDto pageableOrder(int page, int pageSize, List<OrderDetailDto> orderList) {

                int startIndex = page * pageSize;
                int endIndex = Math.min(startIndex + pageSize, orderList.size());

                List<OrderDetailDto> pageContent = orderList.subList(startIndex, endIndex);

                Page<OrderDetailDto> orderPage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                                orderList.size());

                return new OrderPageDto(orderPage.getContent(), orderPage.getTotalElements(),
                                orderPage.getTotalPages());
        }

        @Transactional
        public List<OrderDetailDto> listRequestedByUser(String id) {
                return repository.findByStatus(StatusOrder.REQUESTED).stream()
                                .filter(order -> {
                                        for (OrderItemModel item : order.getOrders()) {
                                                if (id.equals(instituteRepository
                                                                .findById(orderItemRepository.findById(item.getId())
                                                                                .get().getInstitute().getId())
                                                                .get().getUser().getId())) {
                                                        return true;
                                                }
                                        }
                                        return false;
                                })
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

        }

        @Transactional
        public List<OrderDetailDto> listSentByUser(String id) {
                return repository.findByStatus(StatusOrder.SENT).stream()
                                .filter(order -> {
                                        for (OrderItemModel item : order.getOrders()) {
                                                if (id.equals(instituteRepository
                                                                .findById(orderItemRepository.findById(item.getId())
                                                                                .get().getInstitute().getId())
                                                                .get().getUser().getId())) {
                                                        return true;
                                                }
                                        }
                                        return false;
                                })
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

        }

        @Transactional
        public OrderPageDto listCanceledByUser(String id, int page, int pageSize) {
                List<OrderDetailDto> orderList = repository.findByStatus(StatusOrder.CANCELED).stream()
                                .filter(order -> {
                                        for (OrderItemModel item : order.getOrders()) {
                                                if (id.equals(instituteRepository
                                                                .findById(orderItemRepository.findById(item.getId())
                                                                                .get().getInstitute().getId())
                                                                .get().getUser().getId())) {
                                                        return true;
                                                }
                                        }
                                        return false;
                                })
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

                return pageableOrder(page, pageSize, orderList);
        }

        @Transactional
        public OrderPageDto listDeliveredByUser(String id, int page, int pageSize) {
                List<OrderDetailDto> orderList = repository.findByStatus(StatusOrder.DELIVERED).stream()
                                .filter(order -> {
                                        for (OrderItemModel item : order.getOrders()) {
                                                if (id.equals(instituteRepository
                                                                .findById(orderItemRepository.findById(item.getId())
                                                                                .get().getInstitute().getId())
                                                                .get().getUser().getId())) {
                                                        return true;
                                                }
                                        }
                                        return false;
                                })
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

                return pageableOrder(page, pageSize, orderList);

        }

        @Transactional
        public OrderDetailDto detail(String id) {
                return repository.findById(id)
                                .map(order -> mapper.toDtoDetail(order))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Pedido")));
        }

        @Transactional
        public OrderDetailDto update(OrderSaveDto order, String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
                        if (order.freight() != null) {
                                recordFound.setFreight(order.freight());
                        }
                        if (order.date() != null) {
                                recordFound.setDate(order.date());
                        }
                        recordFound.setUpdated(LocalDateTime.now());
                        return repository.save(recordFound);
                }).map(inst -> mapper.toDto(inst))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Pedido")))));
        }

        @Transactional
        public OrderDetailDto cancel(String id) {
                OrderDetailDto orderDetailDto = mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(order -> {
                        order.setStatus(StatusOrder.CANCELED);
                        order.setUpdated(LocalDateTime.now());
                        return repository.save(order);
                }).map(order -> mapper.toDto(order))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Pedido")))));
                
                String userName = repository.findById(orderDetailDto.id()).get().getUserName();
                String userEmail = repository.findById(orderDetailDto.id()).get().getUserEmail();
                String institute = instituteRepository.findById(repository.findById(orderDetailDto.id()).get().getInstitute().getId()).get().getName();
                sendEmailService.sendCanceledOrder(userName, userEmail, institute );
                return orderDetailDto;
        }

        @Transactional
        public OrderDetailDto deliver(String id) {
                OrderDetailDto orderDetailDto = mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(order -> {
                        order.setStatus(StatusOrder.DELIVERED);
                        order.setUpdated(LocalDateTime.now());
                        return repository.save(order);
                }).map(order -> mapper.toDto(order))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("pedido")))));
                
                

                String userName = repository.findById(orderDetailDto.id()).get().getUserName();
                String userEmail = repository.findById(orderDetailDto.id()).get().getUserEmail();
                String institute = instituteRepository.findById(repository.findById(orderDetailDto.id()).get().getInstitute().getId()).get().getName();
                sendEmailService.sendDeliveredOrder(userName, userEmail, institute );
                return orderDetailDto;
        }

        @Transactional
        public OrderDetailDto send(String id) {
                OrderDetailDto orderDetailDto = mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(order -> {
                        order.setStatus(StatusOrder.SENT);
                        order.setUpdated(LocalDateTime.now());
                        return repository.save(order);
                }).map(order -> mapper.toDto(order))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("pedido")))));
                
                

                String userName = repository.findById(orderDetailDto.id()).get().getUserName();
                String userEmail = repository.findById(orderDetailDto.id()).get().getUserEmail();
                String institute = instituteRepository.findById(repository.findById(orderDetailDto.id()).get().getInstitute().getId()).get().getName();
                sendEmailService.sendSentOrder(userName, userEmail, institute );
                return orderDetailDto;
        }

        @Transactional
        public List<OrderDetailDto> listRequestedByInstitute(String id) {
                return repository.findByStatus(StatusOrder.REQUESTED).stream()
                                .filter(order -> id.equals(order.getInstitute().getId()))
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

        }

        @Transactional
        public List<OrderDetailDto> listSentByInstitute(String id) {
                return repository.findByStatus(StatusOrder.SENT).stream()
                                .filter(order -> id.equals(order.getInstitute().getId()))
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

        }

        @Transactional
        public OrderPageDto listCanceledByInstitute(String id, int page, int pageSize) {
                List<OrderDetailDto> orderList = repository.findByStatus(StatusOrder.CANCELED).stream()
                .filter(order -> id.equals(order.getInstitute().getId()))
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());
                return pageableOrder(page, pageSize, orderList);
        }

        @Transactional
        public OrderPageDto listDeliveredByInstitute(String id, int page, int pageSize) {
                List<OrderDetailDto> orderList = repository.findByStatus(StatusOrder.DELIVERED).stream()
                .filter(order -> id.equals(order.getInstitute().getId()))
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

                return pageableOrder(page, pageSize, orderList);

        }

}