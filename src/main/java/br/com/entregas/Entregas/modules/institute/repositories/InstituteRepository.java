package br.com.entregas.Entregas.modules.institute.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.institute.models.InstituteModel;

@CrossOrigin(origins = "*")
public interface InstituteRepository extends JpaRepository<InstituteModel, String>{
    Page<InstituteModel> findByActivedTrue(Pageable pageable);
    Page<InstituteModel> findByActivedFalse(Pageable pageable);
    Optional<InstituteModel> findByWhatsapp(String whatsapp);
    Optional<InstituteModel> findByName(String whatsapp);
}
