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
import br.com.entregas.Entregas.modules.order.dtos.OrderDetailDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderPageDto;
import br.com.entregas.Entregas.modules.order.dtos.OrderSaveDto;
import br.com.entregas.Entregas.modules.order.dtos.mapper.OrderMapper;
import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.order.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
        private OrderRepository repository;
        private OrderMapper mapper;

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
        public List<OrderDetailDto> listRequestedByUser() {
                return repository.findByStatus(StatusOrder.REQUESTED).stream()
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

        }

        @Transactional
        public List<OrderDetailDto> listSentByUser() {
                return repository.findByStatus(StatusOrder.SENT).stream()
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

        }

        @Transactional
        public OrderPageDto listCanceledByUser(int page, int pageSize) {
                List<OrderDetailDto> orderList = repository.findByStatus(StatusOrder.CANCELED).stream()
                                .map(order -> mapper.toDtoDetail(order)).collect(Collectors.toList());

                return pageableOrder(page, pageSize, orderList);
        }

        @Transactional
        public OrderPageDto listDeliveredByUser(int page, int pageSize) {
                List<OrderDetailDto> orderList = repository.findByStatus(StatusOrder.DELIVERED).stream()
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
        public OrderDetailDto save(OrderSaveDto order) {
                OrderSaveDto newOrder = new OrderSaveDto(
                                order.id(),
                                StatusOrder.REQUESTED,
                                order.price(),
                                order.freight(),
                                order.date());
                return mapper.toDtoDetail(repository.save(mapper.toEntity(newOrder)));
        }

        @Transactional
        public OrderDetailDto update(OrderSaveDto order, String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
                        if (order.price() != null) {
                                recordFound.setPrice(order.price());
                        }
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
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }

        @Transactional
        public OrderDetailDto cancel(String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(order -> {
                        order.setStatus(StatusOrder.CANCELED);
                        order.setUpdated(LocalDateTime.now());
                        return repository.save(order);
                }).map(order -> mapper.toDto(order))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }

        @Transactional
        public OrderDetailDto deliver(String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(order -> {
                        order.setStatus(StatusOrder.DELIVERED);
                        order.setUpdated(LocalDateTime.now());
                        return repository.save(order);
                }).map(order -> mapper.toDto(order))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }

}
