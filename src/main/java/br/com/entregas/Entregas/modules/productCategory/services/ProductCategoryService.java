package br.com.entregas.Entregas.modules.productCategory.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategoryDetailDto;
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategoryPageDto;
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategorySaveDto;
import br.com.entregas.Entregas.modules.productCategory.dtos.mapper.ProductCategoryMapper;
import br.com.entregas.Entregas.modules.productCategory.repositories.ProductCategoryRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductCategoryService {
    private ProductCategoryRepository repository;
    private ProductCategoryMapper mapper;

    @Transactional
    public ProductCategoryPageDto listValid(int page, int pageSize) {
        Page<ProductCategoryDetailDto> productCategoryPage = repository
                .findByActivedTrue(PageRequest.of(page, pageSize))
                .map(productCategory -> mapper.toDtoDetail(productCategory));
        return new ProductCategoryPageDto(productCategoryPage.getContent(), productCategoryPage.getTotalElements(),
                productCategoryPage.getTotalPages());
    }

    @Transactional
    public ProductCategoryPageDto listInvalid(int page, int pageSize) {
        Page<ProductCategoryDetailDto> productCategoryPage = repository
                .findByActivedFalse(PageRequest.of(page, pageSize))
                .map(productCategory -> mapper.toDtoDetail(productCategory));
        return new ProductCategoryPageDto(productCategoryPage.getContent(), productCategoryPage.getTotalElements(),
                productCategoryPage.getTotalPages());
    }

    @Transactional
    public ProductCategoryDetailDto detail(String id) {

        return repository.findById(id).map(productCategory -> mapper.toDtoDetail(productCategory))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Categoria de Produto")));
    }

    @Transactional
    public ProductCategoryDetailDto save(ProductCategorySaveDto productCategory) {
        boolean nameUsed = repository.findByName(mapper.toEntity(productCategory).getName()).isPresent();
        if (nameUsed) {
            throw new DomainException(ExceptionMessageConstant.attributeUsed("Nome"));
        }
        ProductCategorySaveDto newProductCategory = new ProductCategorySaveDto(
                productCategory.id(),
                productCategory.name(),
                true);

        return mapper.toDtoDetail(repository.save(mapper.toEntity(newProductCategory)));
    }

    @Transactional
    public ProductCategoryDetailDto update(ProductCategorySaveDto productCategory, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            if (productCategory.name() != null) {
                if (repository.findByName(mapper.toEntity(productCategory).getName()).isPresent()) {
                    throw new DomainException(ExceptionMessageConstant.attributeUsed("Nome"));
                } else {
                    recordFound.setName(productCategory.name());
                }
            }
            recordFound.setUpdated(LocalDateTime.now());
            return repository.save(recordFound);
        }).map(inst -> mapper.toDto(inst))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Categoria de Produto")))));
    }

    @Transactional
    public ProductCategoryDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(productCategory -> mapper.toDto(productCategory))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Categoria de Produto")))));
    }
}
