package br.com.claudsan.store.adapter.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class ItemViewDTO implements Serializable {
    private Long id;
    private String name;
    private Long quantity;

}
