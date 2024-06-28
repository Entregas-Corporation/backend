package br.com.entregas.Entregas.modules.productItem.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.product.models.ProductModel;
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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "item_produto")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_item_produto")
    private String id;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_product", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private ProductModel product;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private UserModel user;

    @Column(name = "ativo", nullable = false)
    private Boolean actived;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "criado", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "alterado", nullable = false)
    private LocalDateTime updated;

}
