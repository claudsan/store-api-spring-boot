package br.com.claudsan.store.adapter.dto;

import br.com.claudsan.store.application.domain.TypeMovementStock;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockMovementRequestDTO implements Serializable {
    private Long itemId;
    private TypeMovementStock typeMovementStock;
    private Long quantity;
}
