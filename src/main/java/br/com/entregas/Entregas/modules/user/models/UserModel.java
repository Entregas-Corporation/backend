package br.com.entregas.Entregas.modules.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.deliveryman.models.DeliverymanModel;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.user.enums.Role;


@Table(name = "usuario")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_usuario")
    private String id;

    @NotBlank(groups = GroupValidation.Create.class)
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    private String name;
    
    @NotBlank(groups = {GroupValidation.Create.class, GroupValidation.Login.class})
    @Size(max = 255)
    @Email
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "photo")
    private String photo;
    
    @Column(name = "nivel", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<InstituteModel> institutes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<DeliverymanModel> deliverymen = new ArrayList<>();

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "valido", nullable = false)
    private Boolean valid;
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "ativo", nullable = false)
    private Boolean actived;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "criado", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "alterado", nullable = false)
    private LocalDateTime updated;

}
