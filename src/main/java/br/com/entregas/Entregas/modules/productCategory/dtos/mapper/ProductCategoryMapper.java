package br.com.entregas.Entregas.modules.productCategory.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategoryDetailDto;
import br.com.entregas.Entregas.modules.productCategory.dtos.ProductCategorySaveDto;
import br.com.entregas.Entregas.modules.productCategory.models.ProductCategoryModel;

@Component
public class ProductCategoryMapper {
    public ProductCategorySaveDto toDto(ProductCategoryModel productCategory) {
        if (productCategory == null) {
            return null;
        }
        return new ProductCategorySaveDto(productCategory.getId(), productCategory.getName(),
                productCategory.getActived());
    }

    public ProductCategoryDetailDto toDtoDetail(ProductCategoryModel productCategory) {
        if (productCategory == null) {
            return null;
        }
        return new ProductCategoryDetailDto(productCategory.getId(), productCategory.getName(),
                productCategory.getCreated(), productCategory.getUpdated());
    }

    public ProductCategoryModel toEntity(ProductCategorySaveDto productCategoryDto) {
        if (productCategoryDto == null) {
            return null;
        }

        ProductCategoryModel productCategory = new ProductCategoryModel();

        if (productCategoryDto.id() != null) {
            productCategory.setId(productCategoryDto.id());
        }
        productCategory.setName(productCategoryDto.name());
        productCategory.setActived(productCategoryDto.actived());
        productCategory.setUpdated(LocalDateTime.now());

        return productCategory;
    }
}
