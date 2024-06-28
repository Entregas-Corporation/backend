package br.com.entregas.Entregas.modules.order.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.order.enums.StatusOrder;
import br.com.entregas.Entregas.modules.order.models.OrderModel;

@CrossOrigin(origins = "*")
public interface OrderRepository extends JpaRepository<OrderModel, String>{
     List<OrderModel> findByStatus(StatusOrder status);
}
