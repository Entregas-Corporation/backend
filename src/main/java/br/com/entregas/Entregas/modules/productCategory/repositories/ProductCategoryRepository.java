package br.com.entregas.Entregas.modules.productCategory.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.productCategory.models.ProductCategoryModel;

@CrossOrigin(origins = "*")
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryModel, String>{
    Page<ProductCategoryModel> findByActivedTrue(Pageable pageable);
    Page<ProductCategoryModel> findByActivedFalse(Pageable pageable);
    Optional<ProductCategoryModel> findByName(String name);
}
