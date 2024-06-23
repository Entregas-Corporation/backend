package br.com.entregas.Entregas.modules.treatment.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.treatment.enums.TreatmentType;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "atendimento")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TreatmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_atendimento")
    private String id;

    @NotBlank(groups = GroupValidation.Create.class)
    @Size(max = 255)
    @Column(name = "titulo", length = 255, nullable = false)
    private String title;

    @NotBlank(groups = GroupValidation.Create.class)
    @Column(name = "assunto", nullable = false)
    private String subject;
    
    @Enumerated(EnumType.STRING)
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "tipo_atendimento", nullable = false)
    private TreatmentType type;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "remetente", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private UserModel sender;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_estabelecimento", nullable = true)
    @JsonProperty(access = Access.WRITE_ONLY)
    private InstituteModel institute;
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "resolvido", nullable = false)
    private Boolean actived;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "criado", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "alterado", nullable = false)
    private LocalDateTime updated;
}
