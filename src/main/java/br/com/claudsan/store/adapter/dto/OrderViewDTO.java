package br.com.claudsan.store.adapter.dto;

import br.com.claudsan.store.application.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderViewDTO implements Serializable {

    private Long orderId;

    private Date creationDate = new Date();
    private Long quantity;

    private Long quantityServed = 0L;

    @JsonIgnoreProperties({"quantity"})
    private ItemViewDTO item;

    private UserViewDTO user;

    private OrderStatus orderStatus = OrderStatus.PENDING;

    @JsonIgnoreProperties({"item", "typeMovement"})
    private List<StockMovementViewDTO> stockMovements;
}
