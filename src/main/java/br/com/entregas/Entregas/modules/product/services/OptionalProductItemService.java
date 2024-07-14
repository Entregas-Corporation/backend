package br.com.entregas.Entregas.modules.product.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.product.repositories.ProductRepository;
import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;
import br.com.entregas.Entregas.modules.productItem.repositories.ProductItemRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OptionalProductItemService {
    private ProductItemRepository repository;
    private ProductRepository productRepository;
    @Transactional
    public void update(ProductModel product, String id){

        ProductModel productModel = productRepository.findById(product.getId()).get();
        ProductItemModel newProductItem = repository.findById(id).get();

        if (newProductItem.getActived().equals(true)) {
            newProductItem.setPrice(productModel.getPrice());       
        }
        repository.save(newProductItem);
    }
}
