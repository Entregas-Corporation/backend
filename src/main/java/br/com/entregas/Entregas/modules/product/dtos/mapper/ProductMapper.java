package br.com.entregas.Entregas.modules.product.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.product.dtos.ProductDetailDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductSaveDto;
import br.com.entregas.Entregas.modules.product.models.ProductModel;

@Component
public class ProductMapper {
    public ProductSaveDto toDto(ProductModel product) {
        if (product == null) {
            return null;
        }
        return new ProductSaveDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getPrice(),
                product.getQuantity(),
                product.getInstitute(),
                product.getCategory(),
                product.getActived());
    }

    public ProductDetailDto toDtoDetail(ProductModel product) {
        if (product == null) {
            return null;
        }
        return new ProductDetailDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getImage(),
            product.getPrice(),
            product.getQuantity(),
            product.getInstitute().getName(),
            product.getCategory().getName(),
            product.getCreated(),
            product.getUpdated());
    }

    public ProductModel toEntity(ProductSaveDto productDto) {
        if (productDto == null) {
            return null;
        }

        ProductModel product = new ProductModel();

        if (productDto.id() != null) {
            product.setId(productDto.id());
        }
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setImage(productDto.image());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        product.setInstitute(productDto.institute());
        product.setCategory(productDto.category());
        product.setActived(productDto.actived());
        product.setUpdated(LocalDateTime.now());

        return product;
    }
}
