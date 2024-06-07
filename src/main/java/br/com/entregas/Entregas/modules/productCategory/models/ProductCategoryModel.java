package br.com.entregas.Entregas.modules.productCategory.models;

import java.time.LocalDateTime;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "categoria_produto")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductCategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_categoria_produto")
    private String id;

    @NotBlank(groups = GroupValidation.Create.class)
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "ativo", nullable = false)
    private Boolean actived;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "criado", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "alterado", nullable = false)
    private LocalDateTime updated;

}
