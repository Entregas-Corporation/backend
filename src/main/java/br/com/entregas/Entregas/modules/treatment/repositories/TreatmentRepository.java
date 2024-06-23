package br.com.entregas.Entregas.modules.treatment.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.treatment.models.TreatmentModel;

@CrossOrigin(origins = "*")
public interface TreatmentRepository extends JpaRepository<TreatmentModel, String>{
    Page<TreatmentModel> findByActivedTrue(Pageable pageable);
    Page<TreatmentModel> findByActivedFalse(Pageable pageable);
}
