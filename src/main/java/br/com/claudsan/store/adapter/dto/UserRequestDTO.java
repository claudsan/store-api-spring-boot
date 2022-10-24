package br.com.claudsan.store.adapter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String email;
}
