package br.com.entregas.Entregas.modules.productItem.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.entregas.Entregas.modules.productItem.dtos.ProductItemDetailDto;
import br.com.entregas.Entregas.modules.productItem.dtos.ProductItemSaveDto;
import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;


@Component
public class ProductItemMapper {
    public ProductItemSaveDto toDto(ProductItemModel productItem) {
        if (productItem == null) {
            return null;
        }
        return new ProductItemSaveDto(
                productItem.getId(),
                productItem.getProduct(),
                productItem.getUser(),
                productItem.getQuantity(),
                productItem.getPrice(),
                productItem.getActived()
                );
    }

    public ProductItemDetailDto toDtoDetail(ProductItemModel productItem) {
        if (productItem == null) {
            return null;
        }
        return new ProductItemDetailDto(
            productItem.getId(), 
            productItem.getProduct().getName(), 
            productItem.getQuantity(), 
            productItem.getPrice(), 
            productItem.getCreated(), 
            productItem.getUpdated());
    }

    public ProductItemModel toEntity(ProductItemSaveDto productItemDto) {
        if (productItemDto == null) {
            return null;
        }

        ProductItemModel productItem = new ProductItemModel();

        if (productItemDto.id() != null) {
            productItem.setId(productItemDto.id());
        }
        productItem.setUser(productItemDto.user());
        productItem.setProduct(productItemDto.product());
        productItem.setQuantity(productItemDto.quantity());
        productItem.setPrice(productItemDto.price());
        productItem.setActived(productItemDto.actived());
        productItem.setUpdated(LocalDateTime.now());

        return productItem;
    }
}
