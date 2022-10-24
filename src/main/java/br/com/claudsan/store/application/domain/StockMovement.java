package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.StockMovementViewDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "stock_mov")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockMovement implements BaseDomain {

    @Id
    @SequenceGenerator(name = "stockMovEntitySeq", sequenceName = "SEQ_STOCK_MOV", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockmovreq")
    @Column(name = "stock_mov_id")
    private Long stockMovementId;

    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate = new Date();

    @OneToOne
    @JoinColumn(name="item_id", nullable=false)
    @JsonIgnoreProperties({"quantity_avaiable"})
    private Item item;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="order_id", referencedColumnName="order_id")
    @JsonBackReference
    @JsonIgnoreProperties({"item","user"})
    private Order order;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private TypeMovementStock typeMovement;

    @Override
    public StockMovementViewDTO toDTO() {
        return StockMovementViewDTO.builder()
                .item(item.toDTO())
                .typeMovement(typeMovement)
                .quantity(quantity)
                .creationDate(creationDate)
                .stockMovementId(stockMovementId)
                .orderId(order.getOrderId())
                .build();
    }
}
