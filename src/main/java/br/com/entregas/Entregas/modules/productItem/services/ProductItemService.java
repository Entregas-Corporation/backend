package br.com.entregas.Entregas.modules.productItem.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.productItem.dtos.ProductItemDetailDto;
import br.com.entregas.Entregas.modules.productItem.dtos.ProductItemSaveDto;
import br.com.entregas.Entregas.modules.productItem.dtos.mapper.ProductItemMapper;
import br.com.entregas.Entregas.modules.productItem.repositories.ProductItemRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductItemService {
        private ProductItemRepository repository;
        private ProductItemMapper mapper;
        private ProductRepository productRepository; 

        @Transactional
        public List<ProductItemDetailDto> listValidByUser(String userId) {
                return repository.findByUserId(userId).stream()
                                .filter(productItem -> productItem.getActived().equals(true))
                                .map(productItem -> mapper.toDtoDetail(productItem)).collect(Collectors.toList());

        }

        @Transactional
        public ProductItemDetailDto detail(String id) {
                return repository.findById(id).filter(productItem -> productItem.getActived().equals(true))
                                .map(productItem -> mapper.toDtoDetail(productItem))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Entregador")));
        }

        @Transactional
        public ProductItemDetailDto save(ProductItemSaveDto productItem) {
                if (productItem.quantity() > productRepository.findById(productItem.product().getId()).get().getQuantity() || productItem.quantity() < 1) {
                        throw new DomainException(ExceptionMessageConstant.invalidQuantity);
                }
                ProductItemSaveDto newProductItem = new ProductItemSaveDto(
                                productItem.id(),
                                productItem.product(),
                                productItem.user(),
                                productItem.quantity(),
                                productItem.quantity() * productRepository.findById(productItem.product().getId()).get().getPrice(),
                                true);

                return mapper.toDtoDetail(repository.save(mapper.toEntity(newProductItem)));
        }

        @Transactional
        public ProductItemDetailDto update(ProductItemSaveDto productItem, String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
                        if (productItem.quantity() != null) {
                                if (productItem.quantity() > productRepository.findById(productItem.product().getId()).get().getQuantity() || productItem.quantity() < 1) {
                                        throw new DomainException(ExceptionMessageConstant.invalidQuantity);
                                }
                                recordFound.setQuantity(productItem.quantity());
                                recordFound.setPrice(productItem.quantity() * productRepository.findById(recordFound.getProduct().getId()).get().getPrice());
                        }
                        recordFound.setUpdated(LocalDateTime.now());
                        return repository.save(recordFound);
                }).map(inst -> mapper.toDto(inst))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }

        @Transactional
        public ProductItemDetailDto toggleActivity(String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                                .map(recordFound -> {
                                        recordFound.setActived(!recordFound.getActived());
                                        recordFound.setUpdated(LocalDateTime.now());
                                        return repository.save(recordFound);
                                }).map(ProductItem -> mapper.toDto(ProductItem))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }
}
