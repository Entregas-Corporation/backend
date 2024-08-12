package br.com.entregas.Entregas.modules.product.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.core.services.upload.CloudinaryUploadService;
import br.com.entregas.Entregas.modules.institute.repositories.InstituteRepository;
import br.com.entregas.Entregas.modules.product.dtos.ProductDetailDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductPageDto;
import br.com.entregas.Entregas.modules.product.dtos.ProductSaveDto;
import br.com.entregas.Entregas.modules.product.dtos.mapper.ProductMapper;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository repository;
    private ProductMapper mapper;
    private InstituteRepository instituteRepository;
    private OptionalProductItemService productItemService;
    private CloudinaryUploadService cloudinaryUploadService;

    public ProductPageDto pageableProduct(int page, int pageSize, List<ProductDetailDto> productList) {

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, productList.size());

        List<ProductDetailDto> pageContent = productList.subList(startIndex, endIndex);

        Page<ProductDetailDto> productPage = new PageImpl<>(pageContent, PageRequest.of(page, pageSize),
                productList.size());

        return new ProductPageDto(productPage.getContent(), productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @Transactional
    public ProductPageDto listValid(int page, int pageSize) {
        List<ProductDetailDto> productList = repository.findAll().stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(true) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductPageDto listInvalid(int page, int pageSize) {
        List<ProductDetailDto> productList = repository.findAll().stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(false) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductPageDto listValidByInstitute(String idInstitute, int page, int pageSize) {
        List<ProductDetailDto> productList = repository.findByInstituteId(idInstitute).stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(true) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductPageDto listInvalidByInstitute(String idInstitute, int page, int pageSize) {
        List<ProductDetailDto> productList = repository.findByInstituteId(idInstitute).stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(false) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductPageDto listValidByCategory(String idCategory, int page, int pageSize) {
        List<ProductDetailDto> productList = repository.findByCategoryId(idCategory).stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(true) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductPageDto listInvalidByCategory(String idCategory, int page, int pageSize) {
        List<ProductDetailDto> productList = repository.findByCategoryId(idCategory).stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(false) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductPageDto listValidByInstituteByCategory(String idInstitute, String idCategory, int page,
            int pageSize) {
        List<ProductDetailDto> productList = repository.findByInstituteIdAndCategoryId(idInstitute, idCategory).stream()
                .filter(product -> instituteRepository.findById(product.getInstitute().getId()).get().getValid()
                        .equals(true) && product.getActived().equals(true) && product.getValid().equals(true))
                .map(product -> mapper.toDtoDetail(product)).collect(Collectors.toList());

        return pageableProduct(page, pageSize, productList);
    }

    @Transactional
    public ProductDetailDto detail(String id) {
        return repository.findById(id).map(product -> mapper.toDtoDetail(product))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Produto")));
    }

    @SuppressWarnings("null")
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
                true,
                true);
        boolean erro = false;

        ProductModel recordFound = null;
        try {
            recordFound = repository.save(mapper.toEntity(newProduct));
        } catch (Exception e) {
            erro = true;
        } finally {
            if (!erro) {
                MultipartFile file = product.image();
                String imageUrl = cloudinaryUploadService.uploadFile(file, newProduct.id());
                recordFound.setImage(imageUrl);
                repository.save(recordFound);
            }
        }

        return mapper.toDtoDetail(recordFound);
    }

    @SuppressWarnings({ "finally", "null" })
    @Transactional
    public ProductDetailDto update(ProductSaveDto product, String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id).map(recordFound -> {
            if (product.name() != null) {
                recordFound.setName(product.name());
            }
            if (product.description() != null) {
                recordFound.setDescription(product.description());
            }
            if (product.price() != null) {
                recordFound.setPrice(product.price());
            }
            if (product.quantity() != null) {
                recordFound.setQuantity(product.quantity());
            }
            if (product.category() != null) {
                recordFound.setCategory(product.category());
            }
            recordFound.setUpdated(LocalDateTime.now());

            for (ProductItemModel item : recordFound.getProduct_itens()) {
                productItemService.update(recordFound, item.getId());
            }

            ProductModel newRecordFound = null;
            boolean error = false;
            try {
                newRecordFound = repository.save(recordFound);
            } catch (Exception e) {
                error = true;
            } finally {
                if (!error) {
                    MultipartFile file = product.image();
                    if (file != null && !file.isEmpty()) {

                        cloudinaryUploadService.deleteFile(recordFound.getId());
                        String imageUrl = cloudinaryUploadService.uploadFile(file, recordFound.getId());
                        recordFound.setImage(imageUrl);
                    }
                }
                recordFound.setUpdated(LocalDateTime.now());
                return repository.save(newRecordFound);
            }
        }).map(inst -> mapper.toDto(inst, product.image()))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Produto")))));
    }

    @Transactional
    public ProductDetailDto toggleActivity(String id) {
        return mapper.toDtoDetail(mapper.toEntity(repository.findById(id)
                .map(recordFound -> {
                    recordFound.setActived(!recordFound.getActived());
                    recordFound.setUpdated(LocalDateTime.now());
                    return repository.save(recordFound);
                }).map(product -> mapper.toDto(product, null))
                .orElseThrow(() -> new DomainException(ExceptionMessageConstant.notFound("Produto")))));
    }
}
