package br.com.entregas.Entregas.modules.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.product.models.ProductModel;

@CrossOrigin(origins = "*")
public interface ProductRepository extends JpaRepository<ProductModel, String>{
    Page<ProductModel> findByActivedTrue(Pageable pageable);
    Page<ProductModel> findByActivedFalse(Pageable pageable);
}
