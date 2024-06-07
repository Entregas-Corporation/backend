package br.com.entregas.Entregas.modules.user.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.entregas.Entregas.modules.user.models.UserModel;

import java.util.Optional;

@CrossOrigin(origins = "*")
public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
    Page<UserModel> findByValidTrue(Pageable pageable);
    Page<UserModel> findByValidFalse(Pageable pageable);
    Page<UserModel> findByActivedFalse(Pageable pageable);   
}
