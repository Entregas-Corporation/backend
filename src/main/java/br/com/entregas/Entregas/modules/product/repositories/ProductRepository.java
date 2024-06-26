package br.com.entregas.Entregas.modules.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.product.models.ProductModel;

@CrossOrigin(origins = "*")
public interface ProductRepository extends JpaRepository<ProductModel, String>{
    List<ProductModel> findByInstituteId(String institute);
    List<ProductModel> findByCategoryId(String category);
    List<ProductModel> findByInstituteIdAndCategoryId(String institute, String category);
}
