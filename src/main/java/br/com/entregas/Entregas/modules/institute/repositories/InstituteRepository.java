package br.com.entregas.Entregas.modules.institute.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.institute.models.InstituteModel;

@CrossOrigin(origins = "*")
public interface InstituteRepository extends JpaRepository<InstituteModel, String>{
    Optional<InstituteModel> findByWhatsapp(String whatsapp);
    Optional<InstituteModel> findByName(String whatsapp);
    List<InstituteModel> findByUserId(String user);
}
