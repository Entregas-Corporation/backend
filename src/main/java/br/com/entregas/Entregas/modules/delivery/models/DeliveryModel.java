package br.com.entregas.Entregas.modules.delivery.models;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.entregas.Entregas.core.validation.GroupValidation;
import br.com.entregas.Entregas.modules.deliveryman.models.DeliverymanModel;
import br.com.entregas.Entregas.modules.order.models.OrderModel;
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

@Table(name = "entrega")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DeliveryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id_entrega")
    private String id;
  
    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_entregador", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private DeliverymanModel deliveryman;

    @NotNull(groups = {GroupValidation.Create.class})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private OrderModel order;

    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "criado", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @NotNull(groups = GroupValidation.Create.class)
    @Column(name = "alterado", nullable = false)
    private LocalDateTime updated;


}
