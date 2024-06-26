package br.com.entregas.Entregas.modules.deliveryman.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.deliveryman.models.DeliverymanModel;

@CrossOrigin(origins = "*")
public interface DeliverymanRepository extends JpaRepository<DeliverymanModel, String>{
    List<DeliverymanModel> findByInstituteId(String institute);
    List<DeliverymanModel> findByUserId(String user);
}
