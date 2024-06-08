package br.com.entregas.Entregas.modules.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.service.models.ServiceModel;

@CrossOrigin(origins = "*")
public interface ServiceRepository extends JpaRepository<ServiceModel, String>{
    Page<ServiceModel> findByActivedTrue(Pageable pageable);
    Page<ServiceModel> findByActivedFalse(Pageable pageable);
}
