package br.com.entregas.Entregas.modules.delivery.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.delivery.models.DeliveryModel;

@CrossOrigin(origins = "*")
public interface DeliveryRepository extends JpaRepository<DeliveryModel, String>{
    List<DeliveryModel> findByDeliverymanId(String deliveryman);
    List<DeliveryModel> findByOrderId(String order);
}

