package br.com.entregas.Entregas.modules.service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.service.models.ServiceModel;

@CrossOrigin(origins = "*")
public interface ServiceRepository extends JpaRepository<ServiceModel, String>{
    List<ServiceModel> findByInstituteId(String institute);
}
