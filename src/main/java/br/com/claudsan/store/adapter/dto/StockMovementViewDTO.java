package br.com.claudsan.store.adapter.dto;

import br.com.claudsan.store.application.domain.TypeMovementStock;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockMovementViewDTO implements Serializable {

    private Long stockMovementId;

    private Date creationDate;

    @JsonIgnoreProperties({"quantity"})
    private ItemViewDTO item;

    private Long orderId;

    private Long quantity;

    private TypeMovementStock typeMovement;
}
