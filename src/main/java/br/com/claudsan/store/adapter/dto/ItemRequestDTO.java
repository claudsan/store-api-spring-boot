package br.com.claudsan.store.adapter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemRequestDTO {
    private String name;
    private Long quantity;
}
