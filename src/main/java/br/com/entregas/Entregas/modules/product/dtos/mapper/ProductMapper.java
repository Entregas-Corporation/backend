package br.com.entregas.Entregas.modules.product.dtos.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.core.config.UploadConfig;
import br.com.entregas.Entregas.modules.product.dtos.ProductDetailDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductSaveDto;
import br.com.entregas.Entregas.modules.product.models.ProductModel;

@Component
public class ProductMapper {

    @Value("${file.upload-dir.product}")
    private String uploadDir;

    public ProductSaveDto toDto(ProductModel product, MultipartFile file) {
        if (product == null) {
            return null;
        }

        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadConfig.upload(uploadDir, storageFileName, product.getImage(), file);
            product.setImage(storageFileName);
        }

        return new ProductSaveDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                file,
                product.getPrice(),
                product.getQuantity(),
                product.getInstitute(),
                product.getCategory(),
                product.getActived(),
                product.getValid()
                );
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

        MultipartFile file = productDto.image();

        if (file != null && !file.isEmpty()) {
            String storageFileName = file.toString() + file.getSize() + file.getOriginalFilename();
            UploadConfig.upload(uploadDir, storageFileName, product.getImage(), file);
            product.setImage(storageFileName);
        }
        

        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        product.setInstitute(productDto.institute());
        product.setCategory(productDto.category());
        product.setActived(productDto.actived());
        product.setValid(productDto.valid());
        product.setUpdated(LocalDateTime.now());

        return product;
    }
}
