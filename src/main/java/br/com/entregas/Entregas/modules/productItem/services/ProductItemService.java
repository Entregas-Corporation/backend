package br.com.entregas.Entregas.modules.productItem.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.product.dtos.mapper.ProductMapper;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.product.services.ProductService;
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
        private ProductMapper productMapper;
        private ProductService productService;

        @Transactional
        public List<ProductItemDetailDto> listValidByUser(String userId) {
                return repository.findByUserIdAndActivedTrue(userId).stream()
                                .map(productItem -> mapper.toDtoDetail(productItem)).collect(Collectors.toList());

        }

        @Transactional
        public ProductItemDetailDto detail(String id) {
                return repository.findById(id)
                                .map(productItem -> mapper.toDtoDetail(productItem))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")));
        }

        @Transactional
        public ProductItemDetailDto save(ProductItemSaveDto productItem) {
                if (productItem.quantity() > productRepository.findById(productItem.product().getId()).get()
                                .getQuantity() || productItem.quantity() < 1) {
                        throw new DomainException(ExceptionMessageConstant.invalidQuantity);
                }
                ProductItemSaveDto newProductItem = new ProductItemSaveDto(
                                productItem.id(),
                                productItem.product(),
                                productItem.user(),
                                productItem.quantity(),
                                productRepository.findById(productItem.product().getId()).get()
                                                .getPrice(),
                                productItem.orderItem(),
                                true);

                return mapper.toDtoDetail(repository.save(mapper.toEntity(newProductItem)));
        }

        @Transactional
        public ProductItemDetailDto update(ProductItemSaveDto productItem, String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
                        if (productItem.quantity() != null) {
                                if (productItem.quantity() > productRepository.findById(productItem.product().getId())
                                                .get().getQuantity() || productItem.quantity() < 1) {
                                        throw new DomainException(ExceptionMessageConstant.invalidQuantity);
                                }
                                recordFound.setQuantity(productItem.quantity());
                                recordFound.setPrice(productRepository
                                                .findById(recordFound.getProduct().getId()).get().getPrice());
                        }
                        if (productItem.actived() != null) {
                                recordFound.setActived(productItem.actived());
                        }
                        if (productItem.orderItem() != null) {
                                recordFound.setOrderItem(productItem.orderItem());
                        }
                        recordFound.setUpdated(LocalDateTime.now());
                        return repository.save(recordFound);
                }).map(inst -> mapper.toDto(inst))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }

        @Transactional
        public ProductItemDetailDto updateByOrder(ProductItemSaveDto productItem, String id) {
                return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
                        ProductModel product = productRepository.findById(recordFound.getProduct().getId()).get();

                        if (productItem.actived() != null) {
                                if (recordFound.getQuantity() > product.getQuantity()) {
                                        throw new DomainException(ExceptionMessageConstant.invalidQuantity);
                                }

                                product.setQuantity(product.getQuantity() - recordFound.getQuantity());
                                productService.update(productMapper.toDto(product, null), product.getId());
                                recordFound.setActived(productItem.actived());
                        }

                        recordFound.setUpdated(LocalDateTime.now());
                        return repository.save(recordFound);
                }).map(inst -> mapper.toDto(inst))
                                .orElseThrow(() -> new DomainException(
                                                ExceptionMessageConstant.notFound("Item de Produto")))));
        }

        @Transactional
        public void toggleActivity(String id) {
                if (!repository.findById(id).isPresent()) {
                        throw new DomainException(
                                        ExceptionMessageConstant.notFound("Item de Produto"));
                }
                repository.deleteById(id);
        }
}