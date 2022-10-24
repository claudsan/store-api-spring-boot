package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.OrderViewDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@Table(name = "store_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order implements BaseDomain{

    @Id
    @SequenceGenerator(name = "orderEntitySeq", sequenceName = "SEQ_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordereq")
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate = new Date();

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "quantity value must be greater than zero ")
    private Long quantity;

    @Column(name = "quantity_served")
    private Long quantityServed = 0L;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnoreProperties({"quantityAvaiable"})
    private Item item;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    @JsonIgnoreProperties({"item","typeMovement"})
    private List<StockMovement> stockMovements;

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        if (this.quantity == this.quantityServed)
            this.orderStatus = OrderStatus.FINISHED;
    }

    public void setQuantityServed(Long quantityServed) {
        this.quantityServed = quantityServed;
        if (this.quantity == this.quantityServed)
            this.orderStatus = OrderStatus.FINISHED;
    }

    @Override
    public OrderViewDTO toDTO() {
        return OrderViewDTO.builder()
                .creationDate(creationDate)
                .orderId(orderId)
                .quantity(quantity)
                .user(user.toDTO())
                .item(item.toDTO())
                .quantityServed(quantityServed)
                .orderStatus(orderStatus)
                .stockMovements(stockMovements.stream().map(stockMovement -> stockMovement.toDTO())
                        .collect(Collectors.toList()))
                .build();
    }
}
