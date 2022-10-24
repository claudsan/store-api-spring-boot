package br.com.claudsan.store.adapter.dto;

import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.Order;
import br.com.claudsan.store.application.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
public class OrderRequestDTO implements Serializable {

    @JsonIgnore
    private Date dateRequest = new Date();
    private Long userId;
    private Long itemId;
    private Long quantity;

    public Order toOrder() {
        return Order.builder().creationDate(new Date())
                .item(Item.builder().itemId(itemId).build())
                .user(User.builder().userId(userId).build())
                .quantity(quantity)
                .quantityServed(0L)
                .build();
    }
}
