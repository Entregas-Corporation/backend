package br.com.entregas.Entregas.modules.orderItem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.orderItem.models.OrderItemModel;

@CrossOrigin(origins = "*")
public interface OrderItemRepository extends JpaRepository<OrderItemModel, String>{
}
