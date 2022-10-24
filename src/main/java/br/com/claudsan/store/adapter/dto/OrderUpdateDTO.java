package br.com.claudsan.store.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderUpdateDTO implements Serializable {
    private Long orderID;
    public Long quantity;
    public Long userId;
}
