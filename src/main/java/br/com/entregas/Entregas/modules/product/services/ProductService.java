package br.com.entregas.Entregas.modules.product.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.product.dtos.ProductDetailDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductPageDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductSaveDto;
import br.com.entregas.Entregas.modules.product.dtos.mapper.ProductMapper;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository repository;
    private ProductMapper mapper;

    @Transactional
    public ProductPageDto listValid(int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(product -> mapper.toDtoDetail(product));
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listInvalid(int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedFalse(PageRequest.of(page, pageSize))
                .map(product -> mapper.toDtoDetail(product));
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listValidByInstitute(String idInstitute, int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(product -> {
                    if (product.getInstitute().getId().equals(idInstitute)) {
                        return mapper.toDtoDetail(product);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Estabelecimento"));
                });
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listInvalidByInstitute(String idInstitute, int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedFalse(PageRequest.of(page, pageSize))
                .map(product -> {
                    if (product.getInstitute().getId().equals(idInstitute)) {
                        return mapper.toDtoDetail(product);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Estabelecimento"));
                });
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listValidByCategory(String idCategory, int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(product -> {
                    if (product.getCategory().getId().equals(idCategory)) {
                        return mapper.toDtoDetail(product);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Categoria de Produto"));
                });
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listInvalidByCategory(String idCategory, int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedFalse(PageRequest.of(page, pageSize))
                .map(product -> {
                    if (product.getCategory().getId().equals(idCategory)) {
                        return mapper.toDtoDetail(product);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Categoria de Produto"));
                });
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listValidByInstituteByCategory(String idInstitute, String idCategory, int page, int pageSize) {
        Page<ProductDetailDto> productPage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(product -> {
                    if (product.getInstitute().getId().equals(idInstitute) && product.getCategory().getId().equals(idCategory)) {
                        return mapper.toDtoDetail(product);
                    }
                    throw new DomainException(ExceptionMessageConstant.notFound("Estabelecimento ou Categoria de Produto"));
                });
        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }



    @Transactional
    public ProductDetailDto detail(String id) {
        return repository.findById(id).map(product -> mapper.toDtoDetail(product))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Produto")));
    }

    @Transactional
    public ProductDetailDto save(ProductSaveDto product) {
        ProductSaveDto newProduct = new ProductSaveDto(
            product.id(),
            product.name(),
            product.description(),
            product.image(),
            product.price(),
            product.quantity(),
            product.institute(),
            product.category(),
            true);
        return mapper.toDtoDetail(repository.save(mapper.toEntity(newProduct)));
    }

    @Transactional
    public ProductDetailDto update(ProductSaveDto product, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            if (product.name() != null) {
                recordFound.setName(product.name());
            }
            if (product.description() != null) {
                recordFound.setDescription(product.description());
            }
            if (product.image() != null) {
                recordFound.setImage(product.image());
            }
            if (product.price() != null){
                recordFound.setPrice(product.price());
            }
            if (product.quantity() != null) {
                recordFound.setQuantity(product.quantity());
            }
            if (product.category() != null) {
                recordFound.setCategory(product.category());
            }
            recordFound.setUpdated(LocalDateTime.now());
            return repository.save(recordFound);
        }).map(inst -> mapper.toDto(inst))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Produto")))));
    }

    @Transactional
    public ProductDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(product -> mapper.toDto(product))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Produto")))));
    }
}
