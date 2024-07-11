package br.com.entregas.Entregas.modules.institute.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
import br.com.entregas.Entregas.modules.service.models.ServiceModel;
import br.com.entregas.Entregas.modules.user.models.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "estabelecimento")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InstituteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_estabelecimento")
    private String id;

    @NotBlank(groups = GroupValidation.Create.class)
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String name;

    @NotBlank(groups = GroupValidation.Create.class)
    @Column(name = "descricao", nullable = false)
    private String description;
    
    @NotBlank(groups = GroupValidation.Create.class)
    @Column(name = "imagem", nullable = false)
    private String image;

    @NotBlank(groups = {GroupValidation.Create.class})
    @Size(max = 255)
    @Column(name = "cidade", length = 255, nullable = false)
    private String city;

    @Column(name = "numero")
    private Integer number;

    @Column(name = "complemento")
    private String complement;

    @NotBlank(groups = {GroupValidation.Create.class})
    @Size(max = 255)
    @Column(name = "longitude", length = 255, nullable = false)
    private String longitude;

    @NotBlank(groups = {GroupValidation.Create.class})
    @Size(max = 255)
    @Column(name = "latitude", length = 255, nullable = false)
    private String latitude;

    @NotBlank(groups = {GroupValidation.Create.class})
    @Size(max = 11)
    @Column(name = "whatsapp", length = 11, nullable = false, unique = true)
    private String whatsapp;

    @NotNull(groups = {GroupValidation.Create.class})
    @Column(name = "valor_frete_km", nullable = false)
    private Double freight_cost_km;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private UserModel user;

    @JsonIgnore
    @OneToMany(mappedBy = "institute")
    List<ServiceModel> services = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "institute")
    List<ProductModel> products = new ArrayList<>();
    
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
