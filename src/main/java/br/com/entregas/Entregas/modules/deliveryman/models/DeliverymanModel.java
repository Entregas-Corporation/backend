package br.com.entregas.Entregas.modules.deliveryman.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "entregador")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DeliverymanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_entregador")
    private String id;

    @NotBlank(groups = GroupValidation.Create.class)
    @Column(name = "curriculo", nullable = false)
    private String curriculum;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_estabelecimento", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private InstituteModel institute;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private UserModel user;

    @Column(name = "ativo", nullable = false)
    private Boolean actived;

    @Column(name = "valido", nullable = false)
    private Boolean valid;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "criado", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "alterado", nullable = false)
    private LocalDateTime updated;

}
