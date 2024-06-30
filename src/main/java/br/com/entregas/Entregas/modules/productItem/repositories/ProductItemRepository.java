package br.com.entregas.Entregas.modules.productItem.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;

@CrossOrigin(origins = "*")
public interface ProductItemRepository extends JpaRepository<ProductItemModel, String>{
     List<ProductItemModel> findByUserIdAndActivedTrue(String user);
}
