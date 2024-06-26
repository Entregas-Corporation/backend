package br.com.entregas.Entregas.modules.treatment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.treatment.models.TreatmentModel;

@CrossOrigin(origins = "*")
public interface TreatmentRepository extends JpaRepository<TreatmentModel, String>{
    List<TreatmentModel> findByInstituteId(String institute);
}
