package br.com.entregas.Entregas.modules.product.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.institute.models.InstituteModel;
import br.com.entregas.Entregas.modules.productCategory.models.ProductCategoryModel;
import br.com.entregas.Entregas.modules.productItem.models.ProductItemModel;
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

@Table(name = "produto")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_produto")
    private String id;

    @NotBlank(groups = GroupValidation.Create.class)
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    private String name;

    @NotBlank(groups = GroupValidation.Create.class)
    @Column(name = "descricao", nullable = false)
    private String description;

    @NotBlank(groups = GroupValidation.Create.class)
    @Column(name = "imagem", nullable = false)
    private String image;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "preco", nullable = false)
    private Double price;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_estabelecimento", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private InstituteModel institute;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_categoria_produto", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private ProductCategoryModel category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<ProductItemModel> product_itens = new ArrayList<>();

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
